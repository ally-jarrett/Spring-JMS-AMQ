package com.spring.test.activemq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;

public class TestAMQClass {

	public static String queueName = "TEST-QUEUE";
	public static Session session = null;
	public static Connection connection = null;
	public static Destination destination = null;

	@BeforeClass
	public static void setUp() throws Exception {

		// Create the connection.
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"vm://localhost?broker.persistent=false");
		connection = connectionFactory.createConnection();
		connection.start();

		// Create the session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create the Queue
		destination = session.createQueue(queueName);
		System.out.println("AMQ Broker Started and queue " + queueName + " has been created");
	}

	@AfterClass
	public static void tearDown() {
		try {
			session.close();
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMessages() {
		try {
			
			Assert.notNull(session);
			// Create the producer.
			MessageProducer producer = session.createProducer(destination);

			// Start sending messages
			String msgTo = " Hello ActiveMQ from Ally :: Maven";
			TextMessage message = session.createTextMessage(msgTo);
			System.out.println("Sending message: " + msgTo);

			producer.send(message);

			System.out.println("Message Sent ...");

			MessageConsumer consumer = session.createConsumer(destination);
			Message msg = consumer.receive();

			if (msg instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) msg;
				System.out.println("recived message: " + txtMsg.getText());
			}	
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
