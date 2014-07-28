package com.spring.test.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsUtils;

import com.spring.test.activemq.MessageObject;

public class MessageConsumerBean {

	private static final Logger LOG = Logger.getLogger(MessageConsumerBean.class);

	private JmsTemplate jmsTemplate;
	private Destination destination;
	private Destination textDestination;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
	public void setTextDestination(Destination textDestination) {
		this.textDestination = textDestination;
	}
	
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                System.out.println(((TextMessage) message).getText());
            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }
        else {
            throw new IllegalArgumentException("Message must be of type TextMessage");
        }
    }

	public String recieveTextMessage() {
		TextMessage message = (TextMessage) jmsTemplate.receive(textDestination);
		String response = null;
		try {
			response = message.getText();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return response;
	}

	public MessageObject receiveMessage() {
		ObjectMessage message = (ObjectMessage) jmsTemplate
				.receive(destination);
		try {
			MessageObject messageObj = (MessageObject) message.getObject();
			return messageObj;
		} catch (JMSException e) {
			throw JmsUtils.convertJmsAccessException(e);
		}
	}
}
