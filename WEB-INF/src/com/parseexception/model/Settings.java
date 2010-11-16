package com.parseexception.model;

import java.io.Serializable;
import java.util.*;

import com.parseexception.Log;

public class Settings implements Serializable {
	/************************************************************************************
	 * Initialization
	 ************************************************************************************/
	private static final long serialVersionUID = -8485312067578542047L;
	private int id;
	
	Column uid;
	Column email;
	Column website;
	Column about;
	Column about_raw;
	
	/* static initialization block */
	static 
	{
		createNewSettings = new StatementHandle();
		populate_settings = new StatementHandle();
		
		get_settings = new StatementHandle();
		set_email = new StatementHandle();
		set_website = new StatementHandle();
		set_about = new StatementHandle();
		set_about_raw = new StatementHandle();
		set_uid = new StatementHandle();
	}
	
	/*
	 * Method Name: Settings
	 * Description: Create a new set of user settings in the database
	 */
	private static transient StatementHandle createNewSettings;
	private static transient StatementHandle get_settings;
	private Settings(int uid, boolean createSettings){
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		
		if(createSettings)
		{
			String query = "INSERT INTO settings (uid) VALUES (?)";
			id = DataAccess.createEntry(createNewSettings, query, "id", args);
			
			if(id >= 0)
			{
				Log.log("Succesfully created new settings for uid: " + uid, Log.DEBUG);
				populateSettings();
			}else{
				Log.log("Unable to create new settings for uid: " + uid, Log.FATAL_ERROR);
			}
		}else{
			String query = "SELECT id FROM settings WHERE uid = ?";
			id = DataAccess.queryReturnInt(get_settings, query, args, "id");
			populateSettings();
		}
	}
	
	public static void createSettings(int uid)
	{
		new Settings(uid, true);
	}
	
	public static Settings getSettings(int uid)
	{
		ObjectCache cache = ObjectCache.getInstance();
		Settings s = (Settings) cache.lookupObject(uid, "settings");
		if(s != null)
		{
			return s;
		}else
		{
			s = new Settings(uid, false);
			return s;
		}
	}
	
	private static transient StatementHandle populate_settings;
	private void populateSettings()
	{
		String query = "SELECT * FROM settings WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(id));
		
		uid = new Column("uid", Column.INTEGER);
		email = new Column("email", Column.STRING);
		website = new Column("website", Column.STRING);
		about = new Column("about", Column.STRING);
		about_raw = new Column("about_raw", Column.STRING);
		
		ArrayList<Column> results = new ArrayList<Column>();
		results.add(uid);
		results.add(email);
		results.add(website);
		results.add(about);
		results.add(about_raw);
		
		DataAccess.queryReturnColumns(populate_settings, query, args, results);
		
		// Add to cache
		ObjectCache cache = ObjectCache.getInstance();
		cache.addObject(this, id, "settings");
	}

	/************************************************************************************
	 * Getter & Setter methods for Settings
	 ************************************************************************************/
	/* id */
	public int getId()
	{
		return id;
	}
	
	public boolean setId(int id)
	{
		return false;
	}
	
	/* uid */
	public int getUid()
	{
		return uid.getInt();
	}
	
	private static transient StatementHandle set_uid;
	public boolean setUid(int uid)
	{
		String query = "UPDATE settings SET uid = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_uid, query, args);
		
		if(ret)
		{
			this.uid.setInt(uid);
		}
		return ret;
	}
	
	/* email */
	public String getEmail()
	{
		return email.getString();
	}
	
	private static transient StatementHandle set_email;
	public boolean setEmail(String str)
	{
		String query = "UPDATE settings SET email = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(str);
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_email, query, args);
		
		if(ret)
		{
			this.email.setString(str);
		}
		return ret;
	}
	
	/* website */
	public String getWebsite()
	{
		return website.getString();
	}
	
	private static transient StatementHandle set_website;
	public boolean setWebsite(String str)
	{
		String query = "UPDATE settings SET website = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(str);
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_website, query, args);
		
		if(ret)
		{
			this.website.setString(str);
		}
		return ret;
	}
	
	/* about */
	public String getAbout()
	{
		return about.getString();
	}
	
	private static transient StatementHandle set_about;
	public boolean setAbout(String str)
	{
		String query = "UPDATE settings SET about = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(str);
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_about, query, args);
		
		if(ret)
		{
			this.about.setString(str);
		}
		return ret;
	}
	
	/* about_raw */
	public String getAboutRaw()
	{
		return about_raw.getString();
	}
	
	private static transient StatementHandle set_about_raw;
	public boolean setAboutRaw(String str)
	{
		String query = "UPDATE settings SET about_raw = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(str);
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_about_raw, query, args);
		
		if(ret)
		{
			this.about_raw.setString(str);
		}
		return ret;
	}
	/************************************************************************************
	 * Advanced Operations
	 ************************************************************************************/
	
	/************************************************************************************
	 * Test Driver
	 ************************************************************************************/
}
