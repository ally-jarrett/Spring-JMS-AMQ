package com.spring.test.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MessageListenerTest implements MessageListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageListenerTest.class);

	private JmsTemplate jmsTemplate;

	/**
	 * @return the jmsTemplate
	 */
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	/**
	 * @param jmsTemplate the jmsTemplate to set
	 */
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			try {
				final String request = textMessage.getText();
				LOGGER.info(request);
				Destination destination = textMessage.getJMSReplyTo();
				final String jmsCorrelationID = textMessage.getJMSCorrelationID();
				jmsTemplate.send(destination, new MessageCreator() {

					@Override
					public Message createMessage(Session session) throws JMSException {
						Message msg = session.createTextMessage(request + "response！");
						msg.setJMSCorrelationID(jmsCorrelationID);
						return msg;
					}
				});
			} catch (JMSException e) {
				LOGGER.error("Something Happened!", e);
			}
		}
	}
}