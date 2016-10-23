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
import com.appdirect.exception.UserAlreadyExistsException;

@Service(UnassignUser.HANDLER_NAME)
public class UnassignUser implements AppDirectEventProcessor {

	private static Logger LOGGER = Logger.getLogger(UnassignUser.class);

	public static final String HANDLER_NAME = "UserUnassignment";

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
	@Transactional
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
				LOGGER.info("Removing user with UUID " + event.getCreator().getUuid() + " from subscription ");
				userRepository.delete(user);

				notifyResponse.setSuccess(true);
			}
		}
		finally {
			return notifyResponse;
		} 
	}
}
