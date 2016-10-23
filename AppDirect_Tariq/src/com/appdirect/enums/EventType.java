package com.appdirect.enums;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum EventType {
	SUBSCRIPTION_ORDER,
	SUBSCRIPTION_CANCEL,
	SUBSCRIPTION_CHANGE,
	USER_ASSIGNMENT,
	USER_UNASSIGNMENT
}
