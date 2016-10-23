package com.appdirect.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import net.oauth.OAuthException;
import net.oauth.OAuthProblemException;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import com.appdirect.event.model.Event;
import com.appdirect.event.model.NotificationResponse;
import com.appdirect.event.processor.AppDirectEventProcessor;
import com.appdirect.event.processor.AppDirectEventProcessorFactory;
import com.appdirect.oauth.OAuthClient;

/**
 *  This is the controller for receiving Appdirect Event notifications. 
 * 
 *  Single point of entry to the application. As every initial request from Appdirect is an event notification,
 *  this controller will listen to that notification and decide the handler class at runtime based on incoming event type.
 *  
 *  This class calls the following apis  - 
 *  
 *  - OAuth validate the incoming request
 *  - Sign the event url and get the response xml from Appdirect regarding the event
 *  - Get the appropriate event processor from the factory
 *  - Process the event and send back the notification response
 */

@RestController
@RequestMapping(value = "/eventlistener")
public class AppdirectEventController {

	private static final Logger logger = Logger.getLogger(AppdirectEventController.class);

	@Autowired
	private NotificationResponse notifyResponse;

	@Autowired
	AppDirectEventProcessor eventProcessor;

	@Autowired
	private OAuthClient oauthSignature;

	@Autowired
	private Event event;

	@Autowired
	private AppDirectEventProcessorFactory eventFactory;

	/**
	 * Listening to Subscription/User event. It could be any of the following - order,cancel,change,assign,unassign
	 * 
	 * @param request
	 * @param eventUrl
	 * @return Notification Response - either success as true , if false then optional errorcode
	 */

	public NotificationResponse listenEventNotification(HttpServletRequest request, @RequestParam String eventUrl) {
		try {

			// Validate the incoming request
			oauthSignature.validate(request);

			// Sign the event url and get the event details from AppDirect
			event = oauthSignature.signAndGet(eventUrl);

			// Get the appropriate EventProcessor object based on Event Type from EventProcessorFactory
			eventProcessor = eventFactory.getEventHandler(event.getType());

			//Process the event
			notifyResponse = eventProcessor.process(event);

			return notifyResponse;

		} catch (Exception e) {

			return notifyResponse;
		}
	}
}
