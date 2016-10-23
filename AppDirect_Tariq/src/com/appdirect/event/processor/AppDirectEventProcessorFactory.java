package com.appdirect.event.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.appdirect.enums.EventType;
import com.appdirect.event.service.handlers.CancelSubscription;
import com.appdirect.event.service.handlers.ChangeSubscription;
import com.appdirect.event.service.handlers.CreateSubscription;
import com.appdirect.event.service.handlers.AssignUser;
import com.appdirect.event.service.handlers.UnassignUser;

@Service
public class AppDirectEventProcessorFactory {

	@Autowired
	private AppDirectEventProcessor createSubsrciption;

	@Autowired  
	private AppDirectEventProcessor changeSubscription;

	@Autowired
	private AppDirectEventProcessor cancelSubscription;  

	@Autowired
	private AppDirectEventProcessor assignUser;

	@Autowired
	private AppDirectEventProcessor unassignUser;


	/**
	 * Get the appropriate event handler given the event type
	 * @param type event type
	 * @return event handler for the event type
	 */
	public AppDirectEventProcessor getEventHandler(EventType type) {
		switch (type) {

		case SUBSCRIPTION_ORDER:
			return createSubsrciption;

		case SUBSCRIPTION_CHANGE:
			return changeSubscription;

		case SUBSCRIPTION_CANCEL:
			return cancelSubscription;

		case USER_ASSIGNMENT:
			return assignUser;

		case USER_UNASSIGNMENT:
			return unassignUser;

		default:
			throw new IllegalArgumentException("Unrecognized event type: " + type);
		}
	}

}
