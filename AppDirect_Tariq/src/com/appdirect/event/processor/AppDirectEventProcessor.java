package com.appdirect.event.processor;

import com.appdirect.event.model.Event;
import com.appdirect.event.model.NotificationResponse;

public interface AppDirectEventProcessor {

	/**
	 * Process incoming AppDirect notification event
	 * @param event notification event to process
	 * @return notification response
	 */
	NotificationResponse process(Event event);

}
