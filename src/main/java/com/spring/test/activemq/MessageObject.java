package com.spring.test.activemq;

import java.io.Serializable;

public class MessageObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private String messageID;

	public MessageObject() {
	};

	public MessageObject(String mailId, String message) {
		super();
		this.messageID = messageID;
		this.message = message;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
