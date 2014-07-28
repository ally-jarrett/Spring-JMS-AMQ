package com.spring.test.activemq;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.spring.test.activemq.MessageObject;

public class MessageProducerBean {

	// JMS Template object
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

	public void sendMessage(final MessageObject messageObj) {
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage msg = session.createObjectMessage(messageObj);
				return msg;
			}
		}); 
	}

	public void sendTextMessage(final String textMessage) {
		jmsTemplate.send(textDestination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage();
				message.setText(textMessage);
				return message;
			}
		});
	}

}
