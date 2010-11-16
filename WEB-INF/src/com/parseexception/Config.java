package com.parseexception;

import java.io.*;
import java.util.Properties;

public class Config {
	// Cache variables
	public static final int cacheTimeout_ms;
	public static final int cacheSize_objs;
	
	// Database variables
	public static final int db_maxconn;
	public static final String db_user;
	public static final String db_password;
	public static final String db_url;
	
	// Debug/Logging variables
	public static final String debugLogFile;
	public static final int debugLevel;
	
	// Email variables
	public static final String smtpServer;
	public static final String smtpSpamEmail;
	public static final String smtpCommentsEmail;
	public static final String smtpErrorsEmail;
	public static final String smtpNoReplyEmail;
	
	// Site Name
	public static final String siteName;
	
	// Spam settings
	public static final int spamProbationPeriodDays;
	public static final int spamPostIntervalMinsProbation;
	public static final int spamPostIntervalMinsStandard;
	
	// Captcha settings
	public static final String captchaPrivateKey;
	public static final String captchaPublicKey;
	
	// Openid Settings
	public static final String openidReturnURL;
	
	static
	{
		Properties props = new Properties();
		try
		{
			// Try to read in properties from file
			props.load(new BufferedReader(
						new FileReader(
							"webapps/parseexception/application.properties")));
		}catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
			
		// Cache settings
		cacheTimeout_ms = 1000 * Integer.parseInt(props.getProperty("cacheTimeout", "5"));
		cacheSize_objs = Integer.parseInt(props.getProperty("cacheSize", "50000"));
		
		// Database settings
		db_maxconn = Integer.parseInt(props.getProperty("dbMaxConn", "30"));
		db_user = props.getProperty("dbUser", "root");
		db_password = props.getProperty("dbPassword", "defaultpword");
		db_url = props.getProperty("dbURL", "jdbc:mysql://localhost:3306/parseexceptiondb");
		
		// Debug/Logging settings
		debugLogFile = props.getProperty("debugLogFile");
		debugLevel = Integer.parseInt(props.getProperty("debugLevel", 
									  Integer.toString(Log.MAX_LEVEL)));
		Log.initLogger(debugLogFile, debugLevel);
		
		// Email settings
		smtpServer = props.getProperty("smtpServer", "");
		smtpSpamEmail = props.getProperty("smtpSpamMail", "");
		smtpCommentsEmail = props.getProperty("smtpCommentsEmail", "");
		smtpErrorsEmail = props.getProperty("smtpErrorsEmail", "");
		smtpNoReplyEmail = props.getProperty("smtpNoReplyEmail", "");
		
		// Site name
		siteName = props.getProperty("siteName", "parseexception.com");
		
		// Spam settings
		spamProbationPeriodDays = Integer.parseInt(
				props.getProperty("spamProbationPeriodDays", "14"));
		spamPostIntervalMinsProbation = Integer.parseInt(
				props.getProperty("spamPostIntervalMinsProbation", "1"));
		spamPostIntervalMinsStandard = Integer.parseInt(
				props.getProperty("spamPostIntervalMinsStandard", "0"));
		
		// Captcha settings
		captchaPrivateKey = props.getProperty("captchaPrivateKey",
							"6LcYhAsAAAAAAC9nNwy08e4vQqsrESdUaW9Uk666");
		captchaPublicKey = props.getProperty("captchaPublicKey",
							"6LcYhAsAAAAAAFxpEVSMwCguCW_xx-M9mxapVdRS");
		
		// Openid Settings
		openidReturnURL = props.getProperty("openidReturnURL", "http://localhost:8081/auth.do");
								  
	}
	
}
