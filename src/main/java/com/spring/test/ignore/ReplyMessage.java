package com.spring.test.ignore;

import java.util.concurrent.Semaphore;

import javax.jms.Message;

public class ReplyMessage {

	private Semaphore semaphore = new Semaphore(0);

	private Message message;

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
