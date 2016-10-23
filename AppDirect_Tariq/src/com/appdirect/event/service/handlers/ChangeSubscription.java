package com.appdirect.event.service.handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appdirect.event.processor.AppDirectEventProcessor;
import com.appdirect.entity.Subscription;
import com.appdirect.entity.SubscriptionUser;
import com.appdirect.enums.ResponseErrorCode;
import com.appdirect.enums.SubscriptionState;
import com.appdirect.repository.SubscriptionRepository;
import com.appdirect.repository.UserRepository;
import com.appdirect.util.SubscriptionPopulator;
import com.appdirect.util.SubscriptionUserPopulator;
import com.appdirect.event.model.Event;
import com.appdirect.event.model.NotificationResponse;
import com.appdirect.exception.AccountNotFoundException;

@Service(ChangeSubscription.HANDLER_NAME)
public class ChangeSubscription implements AppDirectEventProcessor {

	private static Logger LOGGER = Logger.getLogger(ChangeSubscription.class);

	public static final String HANDLER_NAME = "SubscriptionChange";

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private NotificationResponse notifyResponse;

	@Autowired
	private Subscription subscription;

	@SuppressWarnings("finally")
	@Override
	@Transactional
	public NotificationResponse process(Event event) {

		try {
			subscription = subscriptionRepository.findOne(event.getCreator().getUuid());
			if (subscription != null) {

				SubscriptionPopulator.populateSubscriptionOrder(subscription, event);
				LOGGER.info("Changing subscription for company with UUID " + event.getCreator().getUuid());
				subscriptionRepository.save(subscription);
				notifyResponse.setSuccess(true);

			} else 
				try {
					notifyResponse.setErrorCode(ResponseErrorCode.ACCOUNT_NOT_FOUND);
					notifyResponse.setSuccess(false);

					throw new AccountNotFoundException();

				} catch (AccountNotFoundException e) {

					e.printStackTrace();
				}
		}
		finally{
			return notifyResponse;
		}
	}
}
