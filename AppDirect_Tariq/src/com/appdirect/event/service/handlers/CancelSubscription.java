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

@Service(CancelSubscription.HANDLER_NAME)
public class CancelSubscription implements AppDirectEventProcessor {

	private static Logger LOGGER = Logger.getLogger(CancelSubscription.class);

	public static final String HANDLER_NAME = "SubscriptionCancel";

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private Subscription subscription;

	@Autowired
	private NotificationResponse notifyResponse;

	@SuppressWarnings("finally")
	@Override
	@Transactional
	public NotificationResponse process(Event event) {

		// Check if Subscription already exists

		try {

			subscription = subscriptionRepository.findOne(event.getCreator().getUuid());
			if (subscription != null) {

				subscriptionRepository.delete(subscription);
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
