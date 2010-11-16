package com.parseexception.model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import com.parseexception.Log;

public class Comment implements Serializable {
	/************************************************************************************
	 * Initialization
	 ************************************************************************************/
	private static final long serialVersionUID = -668907039003193331L;
	private int id = -1;
	
	Column uid;
	Column sid;
	Column body;
	Column body_raw;
	Column timestamp;
	Column path;
	Column editted;
	
	/* Static initialization block */
	static
	{
		createCommentStmt = new StatementHandle();
		populate_comment = new StatementHandle();
		
		set_uid = new StatementHandle();
		set_sid = new StatementHandle();
		set_body = new StatementHandle();
		set_body_raw = new StatementHandle();
		set_timestamp = new StatementHandle();
		set_path = new StatementHandle();
		set_editted = new StatementHandle();
		
		get_children = new StatementHandle();
	}
	
	/*
	 * Method Name: Comment
	 * Description: Creates a new comment in the database w/o parent_id
	 * Arguments: parent_id < 0 indicates there is no parent and null should be inserted
	 */
	private static transient StatementHandle createCommentStmt;
	public Comment(
					int uid,
					int sid,
					String body,
					String body_raw,
					String pfx)
	{
		try
		{
			String query = "INSERT INTO comments (uid, sid, body, body_raw, path) " +
						   "VALUES (?, ?, ?, ?, ?)";
			if(pfx == null)
				pfx = "";
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(new Integer(uid));
			args.add(new Integer(sid));
			args.add(body);
			args.add(body_raw);
			args.add(pfx);
			
			id = DataAccess.createEntry(createCommentStmt, query, "id", args);
			if(id >= 0)
			{
				Log.log("Created comment: " + body , Log.DEBUG);
				populateComment();
				this.setPath(this.getPath() + this.id + ".");
			}else
			{
				Log.log("Unable to create comment: " + body, Log.NONFATAL_ERROR);
			}
		}catch(Exception e){
			Log.log(e, Log.FATAL_ERROR);
		}
	}
	
	/*
	 * Method Name: Comment
	 * Description: Creates an instance of an existing comment
	 */
	private Comment(int id)
	{
		this.id = id;
		populateComment();
	}
	
	private static transient StatementHandle populate_comment;
	private void populateComment()
	{
		String query = "SELECT * FROM comments WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(id));
		
		uid = new Column("uid", Column.INTEGER);
		sid = new Column("sid", Column.INTEGER);
		body = new Column("body", Column.STRING);
		body_raw = new Column("body_raw", Column.STRING);
		timestamp = new Column("timestamp", Column.TIMESTAMP);
		path = new Column("path", Column.STRING);
		editted = new Column("editted", Column.BOOLEAN);
		
		ArrayList<Column> results = new ArrayList<Column>();
		results.add(uid);
		results.add(sid);
		results.add(body);
		results.add(body_raw);
		results.add(timestamp);
		results.add(path);
		results.add(editted);
		
		DataAccess.queryReturnColumns(populate_comment, query, args, results);
		
		// Add to cache
		ObjectCache cache = ObjectCache.getInstance();
		cache.addObject(this, id, "comments");
	}
	
	public static Comment getComment(int id)
	{
		ObjectCache cache = ObjectCache.getInstance();
		Comment c = (Comment) cache.lookupObject(id, "comments");
		if(c != null)
		{
			return c;
		}else
		{
			c = new Comment(id);
			return c;
		}
	}
	
	/************************************************************************************
	 * Getter & Setter methods for comment
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
	
	/* uid */
	public int getUid()
	{
		return uid.getInt();
	}
	
	private static transient StatementHandle set_uid;
	public boolean setUid(int uid)
	{
		String query = "UPDATE comments SET uid = ? WHERE id = ?";
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
	
	/* sid */
	public int getSid()
	{
		return sid.getInt();
	}
	
	private static transient StatementHandle set_sid;
	public boolean setSid(int sid)
	{
		this.sid.setInt(sid);
		String query = "UPDATE comments SET sid = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(sid));
		args.add(new Integer(id));
		return DataAccess.update(set_sid, query, args);
	}

	/* body */
	public String getBody()
	{
		return body.getString();
	}
	
	private static transient StatementHandle set_body;
	public boolean setBody(String body)
	{
		String query = "UPDATE comments SET body = ? WHERE id = ?";
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
	
	/* body_raw */
	public String getBodyRaw()
	{
		return body_raw.getString();
	}
	
	private static transient StatementHandle set_body_raw;
	public boolean setBodyRaw(String body_raw)
	{
		String query = "UPDATE comments SET body_raw = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(body_raw);
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_body_raw, query, args);
		
		if(ret)
		{
			this.body_raw.setString(body_raw);
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
		String query = "UPDATE comments SET timestamp = ? WHERE id = ?";
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
	
	/* prefix */
	public String getPath()
	{
		return path.getString();
	}
	
	private static transient StatementHandle set_path;
	public boolean setPath(String p)
	{
		String query = "UPDATE comments SET path = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(p);
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_path, query, args);
		
		if(ret)
		{
			this.path.setString(p);
		}
		return ret;
	}
	
	/* depth */
	public int getDepth()
	{
		String p = this.getPath();
		int depth = 0;
		for(int i = 0; i < p.length() - 1; i++)
		{
			if(p.charAt(i) == '.')
				depth++;
		}
		
		return depth;
	}
	
	public int getParent_id()
	{
		String p = this.getPath();
		int idx = p.lastIndexOf('.', p.length() - 2);
		return (idx > 0)? Integer.parseInt(p.substring(idx + 1, p.length() - 1)) : 0;
	}
	
	/* editted */
	public boolean getEditted()
	{
		return editted.getBool();
	}
	
	private static transient StatementHandle set_editted;
	public boolean setEditted(boolean editted)
	{
		String query = "UPDATE comments SET editted = ? WHERE id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Boolean(editted));
		args.add(new Integer(id));
		boolean ret = DataAccess.update(set_editted, query, args);
		
		if(ret)
		{
			this.editted.setBool(editted);
		}
		return ret;
	}
	
	/************************************************************************************
	 * Advanced operations
	 ************************************************************************************/
	public boolean delete()
	{
		return this.setBody("[deleted]");
	}
	
	/* get the thread starting from this comment */
	private static transient StatementHandle get_children;
	public List<Comment> getThread()
	{
		ArrayList<Comment> children = new ArrayList<Comment>();
		PreparedStatement stmt = null;
		
		try
		{
			String query = "SELECT id FROM comments WHERE path LIKE ? ORDER BY path ASC, id ASC";
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(this.getPath() + "%");
			
			stmt = DataAccess.query(get_children, query, args);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				children.add(Comment.getComment(rs.getInt("id")));
			}
			rs.close();
			DataAccess.pool.freeConnection(stmt.getConnection());
		}
		catch(Exception e)
		{
			Log.log(e, Log.FATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return children;
	}
		
	/************************************************************************************
	 * Test Driver
	 ************************************************************************************/
	public static void main(String[] args) {
		Comment c = new Comment(1, 1, "", "", "");
		c.setUid(2);
		c.setSid(2);
		c.setBody("Some comment text");
		
		
		System.out.println("id: " + c.getId());
		System.out.println("uid: " + c.getUid());
		System.out.println("sid: " + c.getSid());
		System.out.println("body: " + c.getBody());
		System.out.println("timestamp: " + c.getTimestamp());
		System.out.println("path: " + c.getPath());
		System.out.println("depth: " + c.getDepth());
		
		System.out.println();
		c = new Comment(1);
		List<Comment> thread = c.getThread();
		for(int i = 0; i < thread.size(); i++)
		{
			c = thread.get(i);
			for(int j = 0; j < c.getDepth(); j++)
				System.out.print("\t");
			System.out.println(c.getBody());
		}
	}
}
