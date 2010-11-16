package com.parseexception.displaybeans;

import java.io.Serializable;

import com.parseexception.StringProcessor;
import com.parseexception.model.*;

public class UserBean implements Serializable {
	private static final long serialVersionUID = 2293625464774566177L;
	private User thisUser;
	private boolean isFriend;
	private boolean isLoggedInUser;
	
	/* ctor */
	public UserBean(User u, User curUser)
	{
		thisUser = u;
		isFriend = (curUser != null)? curUser.getIsFriend(u.getUid()) : false;
		isLoggedInUser = (curUser != null)? curUser.getUid() == thisUser.getUid() : false;
		
	}
	
	/* uid */
	public String getUid()
	{
		return Integer.toString(thisUser.getUid());
	}
	
	/* username */
	public String getUsername()
	{
		return thisUser.getUsername();
	}
	
	/* email */
	public String getEmail()
	{
		Settings s = Settings.getSettings(thisUser.getUid());
		return s.getEmail();
	}
	
	/* about */
	public String getAbout()
	{
		Settings s = Settings.getSettings(thisUser.getUid());
		return StringProcessor.goingToDisplay(s.getAbout());
	}
	
	/* website */
	public String getWebsite()
	{
		Settings s = Settings.getSettings(thisUser.getUid());
		return s.getWebsite();
	}
	
	/* karma */
	public String getKarma()
	{
		return Integer.toString(thisUser.getKarma());
	}
	
	/* joinDate */
	public String getJoinDate()
	{
		return thisUser.getJoinDate();
	}
	
	/* isFriend */
	public String getIsFriend()
	{
		return Boolean.toString(isFriend);
	}
	
	/* isLoggedInUser */
	public String getIsLoggedInUser()
	{
		return Boolean.toString(isLoggedInUser);
	}
}
