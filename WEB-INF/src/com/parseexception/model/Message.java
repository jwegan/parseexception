package com.parseexception.model;

import java.io.Serializable;
import java.util.*;

import com.parseexception.Log;

public class Message implements Serializable {
	/************************************************************************************
	 * Initialization
	 ************************************************************************************/
	private static final long serialVersionUID = -1395788872924795653L;
	private int id;
	
	Column from_id;
	Column to_id;
	Column subject;
	Column body;
	Column unread;
	Column deleted;
	Column threadid;
	Column timestamp;
	
	/* Static initialization block */
	static
	{
		createMessageStmt1 = new StatementHandle();
		createMessageStmt2 = new StatementHandle();
		populate_message = new StatementHandle();
		
		get_deleted = new StatementHandle();
		get_unread = new StatementHandle();
		set_from_id = new StatementHandle();
		set_to_id = new StatementHandle();
		set_subject = new StatementHandle();
		set_body = new StatementHandle();
		set_unread1 = new StatementHandle();
		set_unread2 = new StatementHandle();		
		set_deleted1 = new StatementHandle();
		set_deleted2 = new StatementHandle();		
		set_threadid = new StatementHandle();
		set_timestamp = new StatementHandle();
		
		get_thread = new StatementHandle();
	}
	
	/*
	 * Method Name: Message
	 * Description: Creates a new message in the database
	 */
	private static transient StatementHandle createMessageStmt1;
	private static transient StatementHandle createMessageStmt2;	
	public Message(
						int from_id,
						int to_id,
						String subject,
						String body,
						int threadid)
	{
		try
		{
			subject = (subject == null)? "" : subject;
			body = (body == null)? "" : body;
			String query;
			StatementHandle hndl;
			
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(new Integer(from_id));
			args.add(new Integer(to_id));
			args.add(subject);
			args.add(body);
			
			if(threadid <= 0)
			{
				query = "INSERT INTO messages (from_id, to_id, subject, body) " +
						"VALUES (?, ?, ?, ?)";
				hndl = createMessageStmt1;
			}else{
				query = "INSERT INTO messages (from_id, to_id, subject, body, threadid) " +
				"VALUES (?, ?, ?, ?, ?)";
				hndl = createMessageStmt2;
				args.add(new Integer(threadid));
			}
			
			id = DataAccess.createEntry(hndl, query, "id", args);
			if(id >= 0)
			{
				Log.log("Created new private message: " + subject, Log.DEBUG);
				populateMessage();
			}
			else
			{
				Log.log("Unable to create private message: " + subject, Log.NONFATAL_ERROR);
			}
		}catch(Exception e)
		{
			Log.log(e, Log.FATAL_ERROR);
		}
	}
	
	/*
	 * Method Name: Message
	 * Description: Constructor for an existing message
	 */
	private Message(int id)
	{
		this.id = id;
		populateMessage();
	}
	
	private static transient StatementHandle populate_message;
	private void populateMessage()
	{
		String query = "SELECT * FROM messages WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(id));
		
		from_id = new Column("from_id", Column.INTEGER);
		to_id = new Column("to_id", Column.INTEGER);
		subject = new Column("subject", Column.STRING);
		body = new Column("body", Column.STRING);
		unread = new Column("unread", Column.BOOLEAN);
		deleted = new Column("deleted", Column.BOOLEAN);
		threadid = new Column("threadid", Column.INTEGER);
		timestamp = new Column("timestamp", Column.TIMESTAMP);
		
		ArrayList<Column> results = new ArrayList<Column>();
		results.add(from_id);
		results.add(to_id);
		results.add(subject);
		results.add(body);
		results.add(unread);
		results.add(deleted);
		results.add(threadid);
		results.add(timestamp);
		
		DataAccess.queryReturnColumns(populate_message, query, args, results);
		
		// Add to cache
		ObjectCache cache = ObjectCache.getInstance();
		cache.addObject(this, id, "messages");
	}
	
	public static Message getMessage(int id)
	{
		ObjectCache cache = ObjectCache.getInstance();
		Message m = (Message) cache.lookupObject(id, "messages");
		if(m != null)
		{
			return m;
		}else
		{
			m = new Message(id);
			return m;
		}
	}
	
	/************************************************************************************
	 * Getter & Setter methods for message fields 
	 ************************************************************************************/
	/* id */
	public int getId()
	{
		return id;
	}
	
	public boolean setId()
	{
		return false;
	}
	
	/* from_id */
	public int getFrom_id()
	{
		return from_id.getInt();
	}
	
	private static transient StatementHandle set_from_id;
	public boolean setFrom_id(int from_id)
	{
		String query = "UPDATE messages SET from_id = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(from_id));
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_from_id, query, args);
		
		if(ret)
		{
			this.from_id.setInt(from_id);
		}
		return ret;
	}
	
	/* to_id */
	public int getTo_id()
	{
		return to_id.getInt();
	}
	
	private static transient StatementHandle set_to_id;
	public boolean setTo_id(int to_id)
	{
		String query = "UPDATE messages SET to_id = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(to_id));
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_to_id, query, args);
		
		if(ret)
		{
			this.to_id.setInt(to_id);
		}
		return ret;
	}
	
	/* subject */
	public String getSubject()
	{
		return subject.getString();
	}
	
	private static transient StatementHandle set_subject;
	public boolean setSubject(String subject)
	{
		String query = "UPDATE messages SET subject = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(subject);
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_subject, query, args);
		
		if(ret)
		{
			this.subject.setString(subject);
		}
		return ret;
	}
	
	/* body */
	public String getBody()
	{
		return body.getString();
	}
	
	private static transient StatementHandle set_body;
	public boolean setBody(String body)
	{
		String query = "UPDATE messages SET body = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(body);
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_body, query, args);
		
		if(ret)
		{
			this.body.setString(body);
		}
		return ret;
	}
	
	/* unread */
	private static transient StatementHandle get_unread;
	public boolean getUnread()
	{
		// Goes to DB because setter can update other messages than itself
		String query = "SELECT unread FROM messages WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(id));
		return DataAccess.queryReturnBool(get_unread, query, args, "unread");
	}
	
	private static transient StatementHandle set_unread1;
	private static transient StatementHandle set_unread2;
	public boolean setUnread(boolean unread)
	{
		StatementHandle hndl;
		String query;
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Boolean(unread));
		
		if(unread == true)
		{
			query = "UPDATE messages SET unread = ? WHERE id = ?";
			args.add(new Integer(id));
			hndl = set_unread1;
		}else{
			query = "UPDATE messages SET unread = ? WHERE threadid = ?";
			args.add(new Integer(this.getThreadid()));
			hndl = set_unread2;
		}
		
		boolean ret = DataAccess.update(hndl, query, args);
		if(ret)
		{
			this.unread.setBool(unread);
		}
		return ret;
	}
	
	/* deleted */
	private static transient StatementHandle get_deleted;
	public boolean getDeleted()
	{
		// Goes to DB because setter can update other messages than itself
		String query = "SELECT deleted FROM messages WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(id));
		return DataAccess.queryReturnBool(get_deleted, query, args, "deleted");
	}
	
	private static transient StatementHandle set_deleted1;
	private static transient StatementHandle set_deleted2;
	public boolean setDeleted(boolean deleted)
	{
		String query;
		StatementHandle hndl;
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Boolean(deleted));
		
		int threadid = this.getThreadid();
		if(threadid > 0)
		{
			hndl = set_deleted1;
			query = "UPDATE messages SET deleted = ? WHERE threadid = ?";
			args.add(new Integer(threadid));
		}else{
			hndl = set_deleted2;
			query = "UPDATE messages SET deleted = ? WHERE id = ?";
			args.add(new Integer(id));
		}

		boolean ret = DataAccess.update(hndl, query, args);
		if(ret)
		{
			this.deleted.setBool(deleted);
		}
		return ret;
	}
	
	/* threadid */
	public int getThreadid()
	{
		return threadid.getInt();
	}
	
	private static transient StatementHandle set_threadid;
	public boolean setThreadid(int threadid)
	{
		String query = "UPDATE messages SET threadid = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(threadid));
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_threadid, query, args);
		
		if(ret)
		{
			this.threadid.setInt(threadid);
		}
		return ret;
	}
	
	/* timestamp */
	public String getTimestamp()
	{
		return timestamp.getTimestamp();
	}
	
	private static transient StatementHandle set_timestamp;
	public boolean setTimestamp(Date d)
	{
		String query = "UPDATE messages SET timestamp = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(d);
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_timestamp, query, args);
		
		if(ret)
		{
			this.timestamp.setTimestamp(d);
		}
		return ret;
	}
	
	/************************************************************************************
	 * Advanced operations
	 ************************************************************************************/
	private static transient StatementHandle get_thread;
	public ReturnList<Message> getThread(int pageNum)
	{
		int threadid = this.getThreadid();
		if(threadid > 0)
		{
			String query = "SELECT id FROM messages WHERE threadid = ? " + 
			   			   "ORDER BY id DESC";
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(new Integer(threadid));
			return DBQueries.getMessages(get_thread, query, args, pageNum, true);
		}else{
			ReturnList<Message> ret = new ReturnList<Message>();
			ret.list = new ArrayList<Message>();
			ret.list.add(this);
			ret.bMore = false;
			return ret;
		}
	}
	
	/************************************************************************************
	 * Test Driver
	 ************************************************************************************/
	public static void main(String[] args) {
		Message m = new Message(2, 1, "", "", 0);
		m.setFrom_id(1);
		m.setTo_id(2);
		m.setSubject("Howdy");
		m.setBody("Hello World?");
		m.setUnread(false);
		
		System.out.println("id: " + m.getId());
		System.out.println("from_id: " + m.getFrom_id());
		System.out.println("to_id: " + m.getTo_id());
		System.out.println("subject: " + m.getSubject());
		System.out.println("body: " + m.getBody());
		System.out.println("unread: " + m.getUnread());
		System.out.println("timestamp: " + m.getTimestamp());
	}

}
