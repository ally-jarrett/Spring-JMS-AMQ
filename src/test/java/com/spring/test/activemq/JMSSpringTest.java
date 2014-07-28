package com.spring.test.activemq;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext.xml")
public class JMSSpringTest {

	@Resource
	private MessageProducerBean mbp;

	@Resource
	private MessageConsumerBean mcp;

	public MessageProducerBean getMbp() {
		return mbp;
	}

	public void setMbp(MessageProducerBean mbp) {
		this.mbp = mbp;
	}

	public MessageConsumerBean getMcp() {
		return mcp;
	}

	public void setMbp(MessageConsumerBean mcp) {
		this.mcp = mcp;
	}

	@Test
	public void TestProducerAndConsumer() {
		// Using this test method as a quick hack to ensure unit test ordering execution
		
		// Produce single Object Message
		this.TestProduceObjectMessage();

		// Produce 10 Text JMS Messages
		this.TestProduceTextMessage();

		// If I wasn't lazy I'd assert the queue depths via JMX

		// Consume single Object Message
		this.TestConsumeObjectMessage();

		// Consumer Text Messages
		this.TestConsumeTextMessage();
	}

	public void TestProduceTextMessage() {
		// Send 10 JMS Messages to the text.queue
		for (int i = 0; i < 10; i++) {
			mbp.sendTextMessage("This is a Spring Resource Message on: " + i);
		}
	}

	public void TestProduceObjectMessage() {
		Date date = new Date();
		MessageObject mo = new MessageObject();
		mo.setMessageID(UUID.randomUUID().toString());
		mo.setMessage("This is a message commited on " + date);
		mbp.sendMessage(mo);
	}

	public void TestConsumeObjectMessage() {
		MessageObject mo = mcp.receiveMessage();
		Assert.notNull(mo);
		System.out.println("Consumed message: " + mo.getMessage());
	}

	public void TestConsumeTextMessage() {
		// Consume the 10 messages placed on the queue
		for (int i = 0; i < 10; i++) {
			String recievedMessage = mcp.recieveTextMessage();
			Assert.notNull(recievedMessage);
			System.out.println("Consumed message: " + recievedMessage);
		}
	}

}
