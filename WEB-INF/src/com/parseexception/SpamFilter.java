package com.parseexception;

import java.text.DateFormat;
import java.util.*;

import com.parseexception.model.*;

public class SpamFilter {	
	public static boolean isSpam(User poster, String body, String ip)
	{
		Date lastPost = poster.getLastPost();
		Date curTime = new Date();
		DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, 
			  	  				DateFormat.LONG, Locale.US);
		Date joinDate;
		
		if(poster.getIsMod())
			return false;
		
		try
		{
			joinDate = formatter.parse(poster.getJoinDate());
		}catch(Exception e)
		{
			joinDate = new Date();
		}
		

		if(lastPost != null)
		{
			long diff = curTime.getTime() - lastPost.getTime();
			long userLen = curTime.getTime() - joinDate.getTime();
			long minInterval = (userLen < (Config.spamProbationPeriodDays * 1000 * 60 * 60 * 24))?
							   Config.spamPostIntervalMinsProbation : 
							   Config.spamPostIntervalMinsStandard;
			minInterval *= 1000 * 60;
			if(diff < minInterval)
			{
				Log.log("Filtered post from " + poster.getUsername(), Log.DEBUG);
				return true;
			}
		}
		
		poster.setLastPost(curTime);
		return false;
	}
	
}
