package com.parseexception.displaybeans;

import java.io.Serializable;

import com.parseexception.StringProcessor;
import com.parseexception.model.*;

public class MessageBean implements Serializable {
	private static final long serialVersionUID = -3276231258409116136L;
	private Message thisMessage;
	
	/* ctor */
	public MessageBean(Message m)
	{
		thisMessage = m;
	}
	
	/* id */
	public String getId()
	{
		return Integer.toString(thisMessage.getId());
	}
	
	/* fromUname */
	public String getFromUname()
	{
		User u = User.getUser(thisMessage.getFrom_id());
		return u.getUsername();
	}
	
	/* fromId */
	public String getFromId()
	{
		return Integer.toString(thisMessage.getFrom_id());
	}
	
	/* toUname */
	public String getToUname()
	{
		User u = User.getUser(thisMessage.getTo_id());
		return u.getUsername();
	}
	
	/* toId */
	public String getToId()
	{
		return Integer.toString(thisMessage.getTo_id());
	}
	
	/* subject */
	public String getSubject()
	{
		return thisMessage.getSubject();
	}
	
	/* body */
	public String getBody()
	{
		return StringProcessor.goingToDisplay(thisMessage.getBody());
	}
	
	/* timestamp */
	public String getTimestamp()
	{
		return thisMessage.getTimestamp();
	}
	
	/* unread */
	public boolean getUnread()
	{
		return thisMessage.getUnread();
	}
	
	/* deleted */
	public boolean getDeleted()
	{
		return thisMessage.getDeleted();
	}
}
