package com.appdirect.event.service.handlers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.appdirect.exception.UserAlreadyExistsException;

@Service(AssignUser.HANDLER_NAME)
public class AssignUser implements AppDirectEventProcessor {

	private static Logger LOGGER = Logger.getLogger(AssignUser.class);

	public static final String HANDLER_NAME = "UserAssigment";

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotificationResponse notifyResponse;

	@Autowired
	private Subscription subscription;

	@SuppressWarnings("finally")
	@Override
	public NotificationResponse process(Event event) {

		//Check if subscription & user already exists

		SubscriptionUser user = userRepository.findOne(event.getCreator().getUuid());
		subscription = subscriptionRepository.findOne(event.getCreator().getUuid());

		try {

			if(subscription == null)
			{
				try {

					notifyResponse.setErrorCode(ResponseErrorCode.ACCOUNT_NOT_FOUND);
					notifyResponse.setSuccess(false);

					throw new AccountNotFoundException();

				} catch (AccountNotFoundException e) {

					e.printStackTrace();
				}
			}

			if (user != null) {

				try {
					notifyResponse.setErrorCode(ResponseErrorCode.USER_ALREADY_EXISTS);
					notifyResponse.setSuccess(false);

					throw new UserAlreadyExistsException();
					} 

				catch (UserAlreadyExistsException e) {

					e.printStackTrace();
				}
			}

			else {			

				// Create the subscription	
				subscription = new Subscription();
				SubscriptionPopulator.populateNewSubcription(subscription, event);
				subscription.setState(SubscriptionState.ACTIVE);
				LOGGER.info("Creating subscription for company with UUID " + subscription.getCompanyUuid());
				subscriptionRepository.save(subscription);

				// Create user to assign to the subscription
				user = new SubscriptionUser();

				SubscriptionUserPopulator.populateSubscriptionCreator(user, event);
				user.setSubscription(subscription);
				LOGGER.info("Creating administrator user with UUID " + user.getUuid() + " for company with UUID " + subscription.getCompanyUuid());
				userRepository.save(user);

				notifyResponse.setSuccess(true);
			}
		}

		finally {
			return notifyResponse;
		}  
	}
}
