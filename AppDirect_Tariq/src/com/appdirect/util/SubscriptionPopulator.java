package com.appdirect.util;

import com.appdirect.entity.Subscription;
import com.appdirect.event.model.Company;
import com.appdirect.event.model.Event;
import com.appdirect.event.model.Marketplace;
import com.appdirect.event.model.Order;
import com.appdirect.event.model.OrderItem;

public final class SubscriptionPopulator {

	private SubscriptionPopulator() {
		// static class
	}

	public static void populateNewSubcription(Subscription subscription, Event event) {
		populateCompany(subscription, event);
		populateMarketplace(subscription, event);
		populateSubscriptionOrder(subscription, event);
	}

	public static void populateCompany(Subscription subscription, Event event) {
		Company company = event.getPayload().getCompany();
		subscription.setCompanyUuid(company.getUuid());
		subscription.setCompanyName(company.getName());
		subscription.setCompanyCountry(company.getCountry());
		subscription.setCompanyWebsite(company.getWebsite());
	}

	public static void populateMarketplace(Subscription subscription, Event event) {
		Marketplace marketplace = event.getMarketplace();
		subscription.setMarketplaceUrl(marketplace.getBaseUrl());
		subscription.setMarketplacePartner(marketplace.getPartner());
	}

	public static void populateSubscriptionOrder(Subscription subscription, Event event) {
		Order order = event.getPayload().getOrder();
		subscription.setEditionCode(order.getEditionCode());

		OrderItem orderItem = order.getOrderItem();
		if (orderItem != null) {
			subscription.setMaxOrderItems(orderItem.getQuantity());
		}
	}

}
