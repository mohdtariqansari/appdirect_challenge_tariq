package com.appdirect.event.model;


import com.appdirect.enums.EventType;
import com.appdirect.event.model.Payload;
import com.appdirect.event.model.Marketplace;
import com.appdirect.event.model.User;

public abstract class Event {

	private EventType type;
	private Marketplace marketplace;
	private User creator;
	private Payload payload;

	public Event(EventType type) {
		this.type = type;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Marketplace getMarketplace() {
		return marketplace;
	}

	public void setMarketplace(Marketplace marketplace) {
		this.marketplace = marketplace;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public Payload getPayload() {		
		return payload;
	}
}
