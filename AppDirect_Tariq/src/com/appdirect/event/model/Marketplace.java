package com.appdirect.event.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Marketplace {
	private String partner;
	private String baseUrl;
	public String getBaseUrl() {
		
		return baseUrl;
	}
	public String getPartner() {
		
		return partner;
	}
}
