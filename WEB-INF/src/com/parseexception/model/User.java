package com.parseexception.model;

import java.io.Serializable;
import java.util.*;

import com.parseexception.*;


public class User implements Serializable {
	/************************************************************************************
	 * Initialization
	 ************************************************************************************/
	private static final long serialVersionUID = -3932073187541562192L;
	private int uid = -1;
	
	Column username;
	Column join_date;
	Column is_mod;
	Column karma;
	Column oid_identity;
	Column oid_server;
	Column login_cookie;
	
	private Date lastPost = null;
	
	private static transient StatementHandle get_solutions[];
	
	/* Static initialization block */
	static
	{
		newUserStmt = new StatementHandle();
		populate_user = new StatementHandle();
		
		set_username = new StatementHandle();
		set_join_date = new StatementHandle();
		set_is_mod = new StatementHandle();
		set_karma = new StatementHandle();
		inc_karma = new StatementHandle();
		dec_karma = new StatementHandle();
		set_oid_identity = new StatementHandle();
		set_oid_server = new StatementHandle();
		set_login_cookie = new StatementHandle();
		
		get_solutions = new StatementHandle[4];
		for(int i = 0; i < get_solutions.length; i++)
			get_solutions[i] = new StatementHandle();

		get_liked_solutions = new StatementHandle();
		get_filtered_liked_solutions = new StatementHandle();
		
		get_comments = new StatementHandle();
		
		add_friend = new StatementHandle();
		remove_friend = new StatementHandle();
		get_friends = new StatementHandle();
		get_is_friend = new StatementHandle();
		
		get_received_messages = new StatementHandle();
		get_sent_messages = new StatementHandle();
		
		set_saved_solution = new StatementHandle();
		remove_saved_solution = new StatementHandle();
		get_saved_solutions = new StatementHandle();
		get_filtered_saved_solutions = new StatementHandle();
	}
	
	/*
	 * Method Name: User
	 * Description: Creates a new user in the database
	 */
	private static transient StatementHandle newUserStmt;
	public User(    String oid_identity, 
					String oid_server)
	{
		try
		{
			String query = "INSERT INTO users (username, oid_identity, oid_server) " +
						   "VALUES (?, ?, ?)";
			ArrayList<Object> args = new ArrayList<Object>();
			args.add("newuser");
			args.add(oid_identity);
			args.add(oid_server);
			
			uid = DataAccess.createEntry(newUserStmt, query, "uid", args);
			if(uid >= 0){
				Log.log("Created new user: " + username, Log.DEBUG);
		
				// Create default settings
				Settings.createSettings(uid);
				populateUser();
				
				setUsername("user" + uid);
			}else{
				Log.log("Unable to create user: " + username, Log.NONFATAL_ERROR);
			}
		}catch(Exception e){
			Log.log(e, Log.FATAL_ERROR);
		}
	}
	
	/*
	 * Method Name: User
	 * Description: Instance of an existing user
	 */
	private User(int uid)
	{
		this.uid = uid;
		populateUser();
	}
	
	private static transient StatementHandle populate_user;
	private void populateUser(){
		String query = "SELECT * FROM users WHERE uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		
		username = new Column("username", Column.STRING);
		join_date = new Column("join_date", Column.TIMESTAMP);
		is_mod = new Column("is_mod", Column.BOOLEAN);
		karma = new Column("karma", Column.INTEGER);
		oid_identity = new Column("oid_identity", Column.STRING);
		oid_server = new Column("oid_server", Column.STRING);
		login_cookie = new Column("login_cookie", Column.INTEGER);
		
		ArrayList<Column> results = new ArrayList<Column>();
		results.add(username);
		results.add(join_date);
		results.add(is_mod);
		results.add(karma);
		results.add(oid_identity);
		results.add(oid_server);
		results.add(login_cookie);
		
		DataAccess.queryReturnColumns(populate_user, query, args, results);
		
		// Add to cache
		ObjectCache cache = ObjectCache.getInstance();
		cache.addObject(this, uid, "users");
	}
	
	public static User getUser(int uid)
	{
		if(uid <= 0)
			return null;
		
		ObjectCache cache = ObjectCache.getInstance();
		User u = (User) cache.lookupObject(uid, "users");
		if(u != null)
		{
			return u;
		}else
		{
			u = new User(uid);
			return u;
		}
	}

	/************************************************************************************
	 * Getter & Setter methods for user fields 
	 ************************************************************************************/
	/* uid */
	public int getUid()
	{
		return uid;
	}
	
	public boolean setUid(int uid)
	{
		return false;
	}
	
	/* username */
	public String getUsername()
	{
		return username.getString();
	}
	
	private static transient StatementHandle set_username;
	public boolean setUsername(String uname)
	{
		String query = "UPDATE users SET username = ? WHERE uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(uname);
		args.add(uid);
		boolean ret = DataAccess.update(set_username, query, args);
		
		if(ret)
		{
			this.username.setString(uname);
		}
		return ret;
	}
	
	/* join_date */
	public String getJoinDate()
	{
		return join_date.getTimestamp();
	}
	
	private static transient StatementHandle set_join_date;
	public boolean setJoinDate(Date d)
	{
		String query = "UPDATE users SET join_date = ? WHERE uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(d);
		args.add(new Integer(uid));
		boolean ret = DataAccess.update(set_join_date, query, args);
		
		if(ret)
		{
			this.join_date.setTimestamp(d);
		}
		return ret;
	}
	
	/* is_mod */
	public boolean getIsMod()
	{
		return is_mod.getBool();
	}
	
	private static transient StatementHandle set_is_mod;
	public boolean setIsMod(boolean is_mod)
	{
		String query = "UPDATE users SET is_mod = ? WHERE uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Boolean(is_mod));
		args.add(new Integer(uid));
		boolean ret = DataAccess.update(set_is_mod, query, args);
		
		if(ret)
		{
			this.is_mod.setBool(is_mod);
		}
		return ret;
	}
	
	/* karma */
	public int getKarma()
	{
		return karma.getInt();
	}
	
	private static transient StatementHandle set_karma;
	public boolean setKarma(int i)
	{
		String query = "UPDATE users SET karma = ? WHERE uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(i));
		args.add(new Integer(uid));
		boolean ret = DataAccess.update(set_karma, query, args);
		
		if(ret)
		{
			this.karma.setInt(i);
		}
		return ret;
	}
	
	private static transient StatementHandle inc_karma;
	public boolean incrementKarma()
	{
		String query = "UPDATE users SET karma = karma + 1 WHERE uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		boolean ret = DataAccess.update(inc_karma, query, args);
		
		if(ret)
		{
			this.karma.setInt(this.getKarma() + 1);
		}
		return ret;
	}
	
	private static transient StatementHandle dec_karma;
	public boolean decrementKarma()
	{
		String query = "UPDATE users SET karma = karma - 1 WHERE uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		boolean ret = DataAccess.update(dec_karma, query, args);
		
		if(ret)
		{
			this.karma.setInt(this.getKarma() - 1);
		}
		return ret;
	}
	
	/* oid_identity */
	public String getOidIdentity()
	{
		return oid_identity.getString();
	}
	
	private static transient StatementHandle set_oid_identity;
	public boolean setOidIdentity(String ident)
	{
		String query = "UPDATE users SET oid_identity = ? WHERE uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(ident);
		args.add(uid);
		boolean ret = DataAccess.update(set_oid_identity, query, args);
		
		if(ret)
		{
			this.oid_identity.setString(ident);
		}
		return ret;
	}	
	
	/* oid_server */
	public String getOidServer()
	{
		return oid_server.getString();
	}
	
	private static transient StatementHandle set_oid_server;
	public boolean setOidServer(String server)
	{
		String query = "UPDATE users SET oid_server = ? WHERE uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(server);
		args.add(uid);
		boolean ret = DataAccess.update(set_oid_server, query, args);
		
		if(ret)
		{
			this.oid_server.setString(server);
		}
		return ret;
	}
	
	/* login_cookie */
	public int getLoginCookie()
	{
		return login_cookie.getInt();
	}
	
	private static transient StatementHandle set_login_cookie;
	public boolean setLoginCookie(int i)
	{
		String query = "UPDATE users SET login_cookie = ? WHERE uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(i));
		args.add(new Integer(uid));
		boolean ret = DataAccess.update(set_login_cookie, query, args);
		
		if(ret)
		{
			this.login_cookie.setInt(i);
		}
		return ret;
	}
	
	/************************************************************************************
	 * Advanced user operations
	 ************************************************************************************/
	/* submitted solutions (newest ones first) */
	
	public ReturnList<Solution> getSubmittedSolutions(int pageNum, User curUser, String tagstr)
	{
		String query;
		StatementHandle hndl;
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		
		if(curUser != null && curUser.getUid() == this.getUid())
		{
			if(tagstr == null)
			{
				query = "SELECT sid FROM solutions WHERE submitter = ? ORDER BY sid DESC";
				hndl = get_solutions[0];
			}else
			{
				query = "SELECT s.sid FROM solutions s, tags t WHERE s.submitter = ? " +
						"AND s.sid = t.sid AND t.tag LIKE ? ORDER BY s.sid DESC";
				args.add(tagstr);
				hndl = get_solutions[1];
			}
		}else
		{
			if(tagstr == null)
			{
				query = "SELECT sid FROM solutions WHERE submitter = ? AND ispublic = true ORDER BY sid DESC";
				hndl = get_solutions[2];
			}else
			{
				query = "SELECT s.sid FROM solutions s, tags t WHERE s.submitter = ? AND s.ispublic = true " +
						"AND s.sid = t.sid AND t.tag LIKE ? ORDER BY s.sid DESC";
				args.add(tagstr);
				hndl = get_solutions[3];
 			}
		}

		int curUid = (curUser != null)? curUser.getUid() : 0;
		return DBQueries.getSolutions(hndl, query, args, pageNum, curUid, false);
	}
	
	/* liked solutions (newest ones first) */
	private static transient StatementHandle get_liked_solutions;
	private static transient StatementHandle get_filtered_liked_solutions;
	public ReturnList<Solution> getLikedSolutions(int pageNum, User curUser, String tagstr)
	{
		String query;
		StatementHandle hndl;
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		
		if(tagstr == null)
		{
			query = "SELECT DISTINCT sid FROM votes WHERE uid = ? " +
					   "AND upvote = '1' ORDER BY sid DESC";
			hndl = get_liked_solutions;
		}else
		{
			query = "SELECT DISTINCT v.sid FROM votes v, tags t WHERE v.uid = ? " +
					"AND upvote = '1' AND v.sid = t.sid AND t.tag LIKE ? ORDER BY v.sid DESC";
			hndl = get_filtered_liked_solutions;
			args.add(tagstr);
		}
		
		int curUid = (curUser != null)? curUser.getUid() : 0;
		return DBQueries.getSolutions(hndl, query, 
									args, pageNum, curUid, true);
	}
	
	/* comments posted (newest ones first) */
	private static transient StatementHandle get_comments;
	public ReturnList<Comment> getComments(int pageNum)
	{
		String query = "SELECT id FROM comments WHERE uid = ? " +
					   "ORDER BY id DESC";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		return DBQueries.getComments(get_comments, query, args, pageNum);
	}
	
	/* friends */
	private static transient StatementHandle add_friend;
	public boolean addFriend(int friend_id)
	{
		String query = "INSERT INTO friends (uid, friend_id) VALUES (?, ?)";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		args.add(new Integer(friend_id));
		return DataAccess.createEntry(add_friend, query, "id", args) > 0;
	}
	
	private static transient StatementHandle remove_friend;
	public boolean removeFriend(int friend_id)
	{
		String query = "DELETE FROM friends WHERE uid = ? and friend_id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		args.add(new Integer(friend_id));
		return DataAccess.update(remove_friend, query, args);
	}
	
	private static transient StatementHandle get_friends;
	public ReturnList<User> getFriends(int pageNum)
	{
		String query = "SELECT DISTINCT f.friend_id AS uid FROM friends f, users u " +
					   "WHERE f.uid = ? AND f.friend_id = u.uid " +
		   			   "ORDER BY u.username ASC";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
			
		return DBQueries.getUsers(get_friends, query, args, pageNum);
	}
	
	private static transient StatementHandle get_is_friend;
	public boolean getIsFriend(int friendid)
	{
		String query = "SELECT id FROM friends WHERE uid = ? AND friend_id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		args.add(new Integer(friendid));
		int res = DataAccess.queryReturnInt(get_is_friend, query, args, "id");
		return (res > 0);
	}
	
	/* votes */
	public int getVote(int sid)
	{
		Vote v = Vote.getVote(uid, sid);
		return v.getUpvote();
	}
	
	public boolean setVote(int sid, int upvote)
	{
		Vote v = Vote.getVote(uid, sid);
		return v.setUpvote(upvote);
	}
	
	/* messages */
	private static transient StatementHandle get_received_messages;
	public ReturnList<Message> getReceivedMessages(int pageNum)
	{
		String query = "SELECT id FROM messages WHERE to_id = ? AND " +
					   "deleted = 'false' ORDER BY id DESC";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		return DBQueries.getMessages(get_received_messages, query, args, pageNum, true);
	}
	
	private static transient StatementHandle get_sent_messages;
	public ReturnList<Message> getSentMessages(int pageNum)
	{
		String query = "SELECT id FROM messages WHERE from_id = ? " +
					   "ORDER BY id DESC";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		return DBQueries.getMessages(get_sent_messages, query, args, pageNum, true);
	}
	
	/* saves */
	private static transient StatementHandle set_saved_solution;
	public boolean setSavedSolution(int sid)
	{
		String query = "INSERT INTO saved (uid, sid) VALUES (?, ?)";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(this.getUid());
		args.add(sid);
		return DataAccess.createEntry(set_saved_solution, query, "id", args) > 0;
	}
	
	private static transient StatementHandle remove_saved_solution;
	public boolean removeSavedSolution(int sid)
	{
		String query = "DELETE FROM saved WHERE uid = ? AND sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(this.getUid());
		args.add(sid);
		return DataAccess.update(remove_saved_solution, query, args);
	}
	
	/* saved solutions (newest ones first) */
	private static transient StatementHandle get_saved_solutions;
	private static transient StatementHandle get_filtered_saved_solutions;
	public ReturnList<Solution> getSavedSolutions(int pageNum, String tag)
	{
		StatementHandle hndl;
		String query;
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		
		if(tag == null)
		{
			query = "SELECT DISTINCT sid FROM saved WHERE uid = ? ORDER BY id DESC";
			hndl = get_saved_solutions;
		}else
		{
			query = "SELECT DISTINCT s.sid FROM saved s, tags t WHERE s.uid = ? AND " +
					"s.sid = t.sid AND t.tag LIKE ? ORDER BY s.id DESC";
			args.add(tag);
			hndl = get_filtered_saved_solutions;
		}
		
		return DBQueries.getSolutions(hndl, query, args, pageNum, this.getUid(), false);
	}
	
	/************************************************************************************
	 * Runtime stats
	 ************************************************************************************/
	public Date getLastPost()
	{
		return lastPost;
	}
	
	public void setLastPost(Date d)
	{
		lastPost = d;
	}
	/************************************************************************************
	 * Test Driver
	 ************************************************************************************/
	public static void main(String[] args) {

		User u = new User("a", "c");
		//u.setEmail("test@example.com");
		int uid = u.getUid();
		
		u = new User(uid);
		System.out.println("uid: " + u.getUid());
		System.out.println("username: " + u.getUsername());
		
		/*
		User u = new User(1);
		ReturnList<User> list = u.getFriends(0); 
		System.out.println("Size = " + list.list.size());
		*/
		
	}

}
