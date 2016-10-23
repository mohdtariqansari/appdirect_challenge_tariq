package com.appdirect.event.model;


import com.appdirect.event.model.Account;
import com.appdirect.event.model.Company;
import com.appdirect.event.model.User;
import com.appdirect.event.model.Order;

public class Payload {

	private Account account;
	private Order order;
	private Company company;
	private User user;

	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}	

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
