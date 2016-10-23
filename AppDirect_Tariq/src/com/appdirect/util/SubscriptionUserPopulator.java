package com.appdirect.util;

import com.appdirect.entity.SubscriptionUser;
import com.appdirect.event.model.Event;
import com.appdirect.event.model.User;

public final class SubscriptionUserPopulator {

	private SubscriptionUserPopulator() {
		// static class
	}

	public static void populateSubscriptionCreator(SubscriptionUser subscriptionUser, Event event) {
		User creator = event.getCreator();
		populateSubscriptionUser(subscriptionUser, creator);
		subscriptionUser.setSubscriptionAdministrator(true);
	}

	public static void populateSubscriptionUser(SubscriptionUser subscriptionUser, Event event) {
		User user = event.getCreator();
		populateSubscriptionUser(subscriptionUser, user);
	}

	private static void populateSubscriptionUser(SubscriptionUser subscriptionUser, User user) {
		subscriptionUser.setFirstName(user.getFirstName());
		subscriptionUser.setLastName(user.getLastName());
		subscriptionUser.setLanguage(user.getLanguage());
		subscriptionUser.setEmail(user.getEmail());
		subscriptionUser.setUuid(user.getUuid());
	}

}
