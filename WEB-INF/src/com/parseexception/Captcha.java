package com.parseexception;

import javax.servlet.http.HttpServletRequest;
import net.tanesha.recaptcha.*;

public class Captcha {
	private static ReCaptcha captcha = null;
	
	private static void init()
	{
		if(captcha == null)	
		{
			captcha = ReCaptchaFactory.newReCaptcha(Config.captchaPublicKey,
													Config.captchaPrivateKey, 
													false);
		}
	}
	
	public static String getHTML()
	{
		init();
		return captcha.createRecaptchaHtml(null, null);
	}
	
	public static String checkCaptcha(HttpServletRequest request)
	{
		init();
		String remoteAddr = request.getRemoteAddr();
		String challenge = request.getParameter("recaptcha_challenge_field");
		String response = request.getParameter("recaptcha_response_field");
		
		
		ReCaptchaResponse resp = captcha.checkAnswer(remoteAddr, challenge, response);
		String errstr = resp.getErrorMessage();
		
		if(resp.isValid())
		{
			return "success";
		}else if(errstr.equals("invalid-site-public-key") ||
				 errstr.equals("invalid-site-private-key"))
		{
			return "Catpcha configuration error";
		}else if(errstr.equals("recaptcha-not-reachable"))
		{
			return "Error connecting to captcha server";
		}else
		{
			return "Captcha entry is incorrect";
		}
	}
	
}
