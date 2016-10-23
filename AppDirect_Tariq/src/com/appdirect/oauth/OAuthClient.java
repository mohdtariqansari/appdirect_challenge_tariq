package com.appdirect.oauth;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.appdirect.constants.AppDirectConstants;
import com.appdirect.event.model.Event;

import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.SimpleOAuthValidator;
import net.oauth.server.OAuthServlet;

/**
 * Utility class that manage Oauth signature and validation 
 *
 */
@Component
public class OAuthClient {

	private String consumerKey = AppDirectConstants.CONSUMER_KEY;
	private String consumerSecret = AppDirectConstants.CONSUMER_SECRET;

	/**
	 * Validate incoming request.
	 * 
	 * @param  http request object
	 * @throws OAuthException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void validate(HttpServletRequest request) throws OAuthException, IOException, URISyntaxException {

		OAuthMessage oauthMessage=OAuthServlet.getMessage(request,null);

		//Construct an accessor and a consumer
		OAuthConsumer consumer=new OAuthConsumer(null, consumerKey, consumerSecret, null);
		OAuthAccessor accessor=new OAuthAccessor(consumer);

		//	Validating....
		SimpleOAuthValidator validator=new SimpleOAuthValidator();

		validator.validateMessage(oauthMessage,accessor);
	}

	/**
	 * Sign an outgoing request.
	 * 
	 * @param  HttpUrlConnection object
	 * @throws OAuthMessageSignerException
	 * @throws OAuthExpectationFailedException
	 * @throws OAuthCommunicationException
	 */
	public void sign(HttpURLConnection conn) throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException {

		// Construct a consumer
		oauth.signpost.OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);        

		// Sign the connection
		consumer.sign(conn);
	}

	/**
	 * Sign the event url to AppDirect and read the event xml.
	 * 
	 * @param  eventUrl
	 * @throws MalformedURLException
	 * @throws OAuthExpectationFailedException
	 * @throws OAuthCommunicationException
	 * @throws IOException
	 * @throws OAuthMessageSignerException
	 * @throws JAXBException
	 */

	public Event signAndGet(String eventUrl) throws MalformedURLException, IOException, OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, JAXBException
	{
		HttpURLConnection conn = (HttpURLConnection) new URL(eventUrl).openConnection();
		sign(conn);
		conn.connect();

		// Reading response....
		JAXBContext context = JAXBContext.newInstance(Event.class);
		Event event = (Event) context.createUnmarshaller().unmarshal(conn.getInputStream());

		return event;

	}
}
