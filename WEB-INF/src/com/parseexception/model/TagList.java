package com.parseexception.model;

import java.util.*;
import java.sql.*;
import java.io.*;

import com.parseexception.Log;

public class TagList implements Serializable {
	private static final long serialVersionUID = 3683604917851181206L;
	
	private Vector<String> tags;
	private int id;
	
	static
	{
		get_tags_bysolution = new StatementHandle();
		delete_tags = new StatementHandle();
		add_tags = new StatementHandle();
	}
	
	/* Constructor */
	private static transient StatementHandle get_tags_bysolution;
	private TagList(int id)
	{
		this.id = id;
		tags = new Vector<String>();
		
		String query;
		StatementHandle hndl;
		query = "SELECT DISTINCT tag FROM tags WHERE sid = ?";
		hndl = get_tags_bysolution;
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(id));
		
		try
		{
			PreparedStatement stmt = DataAccess.query(hndl, query, args);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				tags.add(rs.getString("tag"));
			}
			
			rs.close();
			DataAccess.pool.freeConnection(stmt.getConnection());
		}catch(Exception e)
		{
			Log.log(e, Log.FATAL_ERROR);
		}
		
		ObjectCache cache = ObjectCache.getInstance();
		cache.addObject(this, id, "tags");
	}
	
	/* Returns an instance of the tag list */
	public static TagList getTagList(int id)
	{
		ObjectCache cache = ObjectCache.getInstance();
		TagList list = (TagList) cache.lookupObject(id, "tags");
		
		if(list == null)
		{
			list = new TagList(id);
		}
		
		return list;
	}
	
	/* Get id */
	public int getId()
	{
		return id;
	}
	
	/* Get tag list */
	public List<String> getTags()
	{
		return tags;
	}
	
	public static String[] parseTags(String tagstr)
	{
		String tagary[] = tagstr.split("\\s*[,;]\\s*");
		return tagary;
	}
	
	/* Set tags */
	private static transient StatementHandle delete_tags;
	private static transient StatementHandle add_tags;
	public boolean setSolutionTags(String tagstr, int uid, int sid)
	{
		
		boolean retval = true;
		String query = "INSERT INTO tags (sid, tag, uid) VALUES (?, ?, ?)";
		ArrayList<Object> args = new ArrayList<Object>();
		String tagary[] = parseTags(tagstr);
		
		// Remove any old tags
		String deleteq = "DELETE FROM tags WHERE sid = ? AND uid = ?";
		args.add(new Integer(sid));
		args.add(new Integer(uid));
		retval &= DataAccess.update(delete_tags, deleteq, args);
		
		// Add all the new tags
		for(int i = 0; i < tagary.length; i++)
		{
			String s = tagary[i].trim();
			if(s.length() <= 0)
				continue;
			
			args.clear();
			args.add(new Integer(id));
			args.add(s);
			args.add(uid);
			retval &= DataAccess.createEntry(add_tags, query, "id", args) > 0;
		}
		
		// Update the tag vector
		if(retval)
		{
			tags.clear();
			for(int i = 0; i < tagary.length; i++)
			{
				String s = tagary[i].trim();
				if(s.length() > 0)
				{
					tags.add(tagary[i]);
				}
			}
		}
		
		return retval;
	}
	
}
