package com.parseexception.model;

import java.util.*;
import java.io.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.openid4java.discovery.*;
import org.openid4java.message.*;
import org.openid4java.OpenIDException;
import org.openid4java.consumer.*;
import com.parseexception.Config;

public class OpenIdManager {
	private static Hashtable<String, DiscoveryInformation> discInfo;
	private static ConsumerManager manager;

	static {
		try {
			discInfo = new Hashtable<String, DiscoveryInformation>();
			manager = new ConsumerManager();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static int getDiscInfoSize() 
	{
		return discInfo.size();
	}
	
	private static void setOidCookie(String provider, HttpServletResponse response)
	{
		Cookie ck = new Cookie("oid_provider", provider);
		ck.setMaxAge(-1);
		response.addCookie(ck);
	}
	
	public static String getOidCookie(HttpServletRequest request)
	{
		Cookie cookies[] = request.getCookies();
		String provider = "";
		
		for(int i = 0; cookies != null && i < cookies.length; i++)
		{
			if(cookies[i].getName().compareTo("oid_provider") == 0)
			{
				provider = cookies[i].getValue();
				break;
			}
		}
		
		return provider;
	}
	

	@SuppressWarnings("unchecked") // manager.discover returns raw List
	public static ActionForward authRequest(String provider, 
									 HttpServletRequest request,
									 HttpServletResponse response) 
									throws IOException, OpenIDException {
		// configure the return_to URL where your application will receive
		// the authentication responses from the OpenID provider
		String returnToUrl = Config.openidReturnURL;

		// --- Forward proxy setup (only if needed) ---
		// ProxyProperties proxyProps = new ProxyProperties();
		// proxyProps.setProxyName("proxy.example.com");
		// proxyProps.setProxyPort(8080);
		// HttpClientFactory.setProxyProperties(proxyProps);

		DiscoveryInformation discovered = discInfo.get(provider);
		if(discovered == null)
		{
			// perform discovery on the user-supplied identifier
			List discoveries = manager.discover(provider);

			// attempt to associate with the OpenID provider
			// and retrieve one service endpoint for authentication
			discovered = manager.associate(discoveries);
			
			// Store discovered info
			discInfo.put(provider, discovered);
		}

		// store the discovery information in the user's session
		setOidCookie(provider, response);

		// obtain a AuthRequest message to be sent to the OpenID provider
		AuthRequest authReq = manager.authenticate(discovered, returnToUrl);
		String destUrl = null;
		ActionForward forward = null;
		if (!discovered.isVersion2()) 
		{
			// Option 1: GET HTTP-redirect to the OpenID Provider endpoint
			// The only method supported in OpenID 1.x
			// redirect-URL usually limited ~2048 bytes
			destUrl = authReq.getDestinationUrl(true);
			forward = new ActionForward(destUrl, true);
			//response.sendRedirect(destUrl);
			return null;
		} else 
		{
			// Option 2: HTML FORM Redirection (Allows payloads >2048 bytes)
			destUrl = authReq.getDestinationUrl(false);
			forward = new ActionForward("/pages/openid/formredirection.jsp", false);
			// RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/openid/formredirection.jsp");
			//request.setAttribute("parameterMap", authReq.getParameterMap());
			request.setAttribute("message", authReq);
			//dispatcher.forward(request, response);
		}
		
		return forward;
	}
	
	public static boolean isRedirect(String provider)
	{
		DiscoveryInformation discovered = discInfo.get(provider);
		return !discovered.isVersion2();
	}

	// --- processing the authentication response ---
	public static String verifyResponse(HttpServletRequest request) 
	{
		try {
			// extract the parameters from the authentication response
			// (which comes in as a HTTP request from the OpenID provider)
			ParameterList response = new ParameterList(request.getParameterMap());

			// retrieve the previously stored discovery information
			String provider = getOidCookie(request);
			DiscoveryInformation discovered = discInfo.get(provider);

			// extract the receiving URL from the HTTP request
			StringBuffer receivingURL = request.getRequestURL();
			String queryString = request.getQueryString();
			if (queryString != null && queryString.length() > 0)
				receivingURL.append("?").append(request.getQueryString());

			// verify the response; ConsumerManager needs to be the same
			// (static) instance used to place the authentication request
			VerificationResult verification = manager.verify(receivingURL
					.toString(), response, discovered);

			// examine the verification result and extract the verified
			// identifier
			Identifier verified = verification.getVerifiedId();
			if (verified != null) {
				//AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();
				return verified.getIdentifier(); // success
			}
		} catch (OpenIDException e) {
			// present error to the user
		}

		return null;
	}
}
