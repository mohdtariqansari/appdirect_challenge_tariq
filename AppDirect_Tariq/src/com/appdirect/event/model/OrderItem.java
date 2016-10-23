package com.appdirect.event.model;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")

public class OrderItem {
    
	private int quantity;
	private String unit;
	
    public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

}
