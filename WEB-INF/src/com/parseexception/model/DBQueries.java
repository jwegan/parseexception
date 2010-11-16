package com.parseexception.model;

import java.util.*;
import java.util.Date;
import java.sql.*;

import com.parseexception.Config;
import com.parseexception.Log;

public class DBQueries {
	public static final int DEFAULT_NUM_SOLUTIONS = 25;
	public static final int DEFAULT_NUM_COMMENTS = 25;
	public static final int DEFAULT_COMMENTS_PER_SOLUTION = 200;
	public static final int DEFAULT_NUM_MESSAGES = 25;
	public static final int DEFAULT_NUM_USERS = 25;
	
	/*************************************************************************
	 * Static initialization block 
	 *************************************************************************/
	private static StatementHandle get_new_solutions;
	private static StatementHandle get_best_solutions;
	private static StatementHandle get_hot_solutions;
	private static StatementHandle[] tag_search;
	static
	{
		logip = new StatementHandle();
		
		validate_identity = new StatementHandle();
		get_unread_msg_cnt = new StatementHandle();
		
		tag_search = new StatementHandle[3];
		for(int i = 0; i < tag_search.length; i++)
			tag_search[i] = new StatementHandle();
		
		get_new_solutions = new StatementHandle();
		get_best_solutions = new StatementHandle();
		get_hot_solutions = new StatementHandle();
		
		get_friend_solutions_liked = new StatementHandle();
		get_friend_solutions_submitted = new StatementHandle();
		get_friend_comments = new StatementHandle();
		search_hndl = new StatementHandle();
		user_search = new StatementHandle();
 	}
	
	/*************************************************************************
	 * Log ip
	 *************************************************************************/
	private static StatementHandle logip;
	public static boolean logIP(int uid, String ipaddr)
	{
		String query = "INSERT INTO iplog (uid, ip) VALUES (?, ?)";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(uid);
		args.add(ipaddr);
		int id = DataAccess.createEntry(logip, query, "id", args);
		return id > 0;
	}
	
	/*************************************************************************
	 * Check username
	 *************************************************************************/
	private static StatementHandle validate_identity;
	public static int getUid(String openid_identity)
	{
		String query = "SELECT uid FROM users WHERE oid_identity = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(openid_identity);
		return DataAccess.queryReturnInt(validate_identity, query, args, "uid");
	}
	
	/*************************************************************************
	 * Unread message count
	 *************************************************************************/
	private static StatementHandle get_unread_msg_cnt;
	public static int getUnreadMessageCount(int uid)
	{
		String query = "SELECT COUNT(id) AS count FROM messages " +
					   "WHERE unread=true AND to_id = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		return DataAccess.queryReturnInt(get_unread_msg_cnt, query, args, "count");
	}
	
	/*************************************************************************
	 * Search
	 *************************************************************************/
	/* search tags */
	public static ReturnList<Solution> tagSearch(String tag, String searchType, 
												int uid, int pageNum)
	{
		String query;
		int queryIdx;
		ArrayList<Object> args = new ArrayList<Object>();
		args.add("%" + tag + "%");
		
		if(searchType.compareTo("dateOldest") == 0)
		{
			query = "SELECT DISTINCT sid FROM tags WHERE tag LIKE ? " +
					"ORDER BY sid ASC";
			queryIdx = 0;
		}else if(searchType.compareTo("dateNewest") == 0){
			query = "SELECT DISTINCT sid FROM tags WHERE tag LIKE ? " +
			"ORDER BY sid DESC";
			queryIdx = 1;
		}else{
			query = "SELECT DISTINCT t.sid AS sid FROM tags t, solutions s " +
			"WHERE t.tag LIKE ? AND t.sid = s.sid " +
			"ORDER BY s.score DESC, s.sid DESC";
			queryIdx = 2;
		}
		
		return getSolutions(tag_search[queryIdx], query, args, pageNum, uid, true);
	}
	
	/* search users */
	private static StatementHandle user_search;
	public static ReturnList<User> userSearch(String uname, int pageNum)
	{
		String query = "SELECT DISTINCT uid FROM users WHERE username LIKE ? " +
			  		   "ORDER BY username ASC";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add("%" + uname + "%");
			
		return getUsers(user_search, query, args, pageNum);
	}
	
	/* search solutions (probably best to use google instead of this) */
	private static StatementHandle search_hndl;
	public static ReturnList<Solution> solutionSearch(String searchstr, String searchType,
												int uid, int pageNum){
		StringBuilder query = new StringBuilder();
		ArrayList<Object> args = new ArrayList<Object>();
		String keywords[] = searchstr.split("\\s+");
		
		// Verify there is at least 1 keyword
		if(keywords.length == 0)
		{
			ReturnList<Solution> ret = new ReturnList<Solution>();
			ret.bMore = false;
			ret.list = new ArrayList<Solution>();
			return ret;
		}
		
		// Process search query
		query.append("SELECT DISTINCT sid FROM solutions WHERE ");
		for(int i = 0; i < keywords.length; i++)
		{
			query.append("(question LIKE ? OR answer LIKE ?) ");
			args.add("%" + keywords[i] + "%");
			args.add("%" + keywords[i] + "%");
			if(i + 1 < keywords.length)
				query.append("AND ");
		}
		
		// Process ordering
		if(searchType.compareTo("dateOldest") == 0)
		{
			query.append("ORDER BY sid ASC");
		}else if(searchType.compareTo("dateNewest") == 0){
			query.append("ORDER BY sid DESC");
		}else{
			query.append("ORDER BY score DESC, sid DESC");
		}
		
		ReturnList<Solution> list = getSolutions(search_hndl, query.toString(), args, 
											pageNum, uid, true);
		search_hndl.clear();
		return list;
	}
	
	/*************************************************************************
	 * Get Solutions
	 *************************************************************************/
	/* Get solutions liked by friends */
	private static StatementHandle get_friend_solutions_liked;
	public static ReturnList<Solution> getSolutionsLikedByFriends(int uid, 
													   		 int pageNum)
	{
		
		String query = "SELECT DISTINCT v.sid FROM votes v, friends f " + 
					   "WHERE v.uid = f.friend_id AND f.uid = ? AND " +
					   "v.upvote = '1' ORDER BY v.sid DESC";

		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		
		return getSolutions(get_friend_solutions_liked, query, 
						  args, pageNum, uid, true);
	}
	
	/* Get solutions submitted by friends */
	private static StatementHandle get_friend_solutions_submitted;
	public static ReturnList<Solution> getSolutionsSubmittedByFriends(int uid,
														   		 int pageNum)
	{
		String query = "SELECT DISTINCT s.sid FROM solutions s, friends f " +
					   "WHERE s.submitter = f.friend_id AND f.uid = ? " +
					   "ORDER BY s.sid DESC";
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));

		return getSolutions(get_friend_solutions_submitted, query, 
						  args, pageNum, uid, true);
	}
	
	/* Select the newest solutions */	
	public static ReturnList<Solution> getNewestSolutions(int uid, int pageNum)
	{
		ArrayList<Object> args = new ArrayList<Object>();
		String query = "SELECT sid FROM solutions WHERE ispublic = true ORDER BY sid DESC";
		return getSolutions(get_new_solutions, query, args, pageNum, uid, true);
	}
	
	/* Get the best solutions */
	public static ReturnList<Solution> getBestSolutions(int uid, int pageNum)
	{
		ArrayList<Object> args = new ArrayList<Object>();
		String query = "SELECT sid FROM solutions WHERE ispublic = true ORDER BY score DESC, sid DESC";
		return getSolutions(get_best_solutions, query, args, pageNum, uid, true);
	}
	
	/* Get hot solutions */
	public static ReturnList<Solution> getHotSolutions(int uid, int pageNum)
	{
		ArrayList<Object> args = new ArrayList<Object>();
		String query = "SELECT sid FROM solutions WHERE score >= 10 AND ispublic = true ORDER BY sid DESC";
		return getSolutions(get_hot_solutions, query, args, pageNum, uid, true);	
	}
	
	/*************************************************************************
	 * Get comments
	 *************************************************************************/
	private static StatementHandle get_friend_comments;
	public static ReturnList<Comment> getFriendComments(int uid, int pageNum)
	{
		String query = "SELECT c.id FROM comments c, friends f WHERE " +
					   "c.uid = f.friend_id AND f.uid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(uid);
		
		return getComments(get_friend_comments, query, args, pageNum);
	}
	
	/*************************************************************************
	 * Helper functions to process results
	 * @param cacheResults
	 *************************************************************************/
	/* helper function to process solution results */
	@SuppressWarnings(value="unchecked")
	public static ReturnList<Solution> getSolutions(StatementHandle hndl, 
										 String query, 
										 List<Object> args, 
										 int pageNum,
										 int uid, boolean cacheResults)
	{
		ReturnList<Solution> ret = null;
		PreparedStatement stmt = null;
		
		try
		{
			int numResults = DEFAULT_NUM_SOLUTIONS;
			query = query + " LIMIT ? OFFSET ?";
			args.add(new Integer(numResults + 1));
			args.add(new Integer(pageNum * numResults));
			
			// The cast below generates a warning due to type erasure when using 
			// Generics Java cannot guarantee the ReturnList contains only instances
			// of Solution. There doesn't seem to be any way around this warning
			Date curTime = new Date();
			ObjectCache cache = ObjectCache.getInstance();
			if(cacheResults)
			{
				ret = (ReturnList<Solution>) cache.lookupObject(query, args, "DBQueries");
				if(ret != null && (curTime.getTime() - ret.creationTime) < 
								   Config.cacheTimeout_ms)
				{
					return ret;
				}
			}
			
			// Create new ReturnList so old one isn't stomped on
			ret = new ReturnList<Solution>();
			stmt = DataAccess.query(hndl, query, args);
			ResultSet rs = stmt.executeQuery();
				
			ret.list.clear();
			for(int i = 0; i < numResults && rs.next(); i++)
			{
				ret.list.add(Solution.getSolution(rs.getInt("sid")));
			}
			
			// Set if there are more results
			if(rs.next())
			{
				ret.bMore = true;
			}
			
			rs.close();
			DataAccess.pool.freeConnection(stmt.getConnection());
			
			if(cacheResults)
			{
				cache.addObject(ret, query, args, "DBQueries");
			}
		}catch(Exception e)
		{
			Log.log(e, Log.NONFATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return (ret == null)? new ReturnList<Solution>() : ret;
	}
	
	/* helper function to process message results */
	@SuppressWarnings("unchecked")
	public static ReturnList<Message> getMessages(StatementHandle hndl, 
												  String query, 
												  List<Object> args, 
												  int pageNum, boolean cacheResults)
	{
		ReturnList<Message> ret = new ReturnList<Message>();
		PreparedStatement stmt = null;
		
		try
		{
			ObjectCache cache = ObjectCache.getInstance();
			if(cacheResults)
			{
				ReturnList<Message> result = (ReturnList<Message>) cache.lookupObject(query, args, new Integer(pageNum), "getMessages");
				if(result != null && ((new Date()).getTime() - result.creationTime) < Config.cacheTimeout_ms)
				{
					return result;
				}
			}
			
			int numResults = DEFAULT_NUM_MESSAGES;
			query = query + " LIMIT ? OFFSET ?";
			args.add(new Integer(numResults + 1));
			args.add(new Integer(pageNum * numResults));

			// Get results
			stmt = DataAccess.query(hndl, query, args);
			ResultSet rs = stmt.executeQuery();
			for(int i = 0; i < numResults && rs.next(); i++)
			{
				ret.list.add(Message.getMessage(rs.getInt("id")));
			}
			
			// Set if there are more results
			if(rs.next())
			{
				ret.bMore = true;
			}
			
			rs.close();
			DataAccess.pool.freeConnection(stmt.getConnection());
			
			if(cacheResults)
			{
				cache.addObject(ret, query, args, new Integer(pageNum), "getMessages");
			}
		}catch(Exception e)
		{
			Log.log(e, Log.NONFATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return ret;
	}
	
	/* helper function to process comment results */
	public static ReturnList<Comment> getComments(StatementHandle hndl, 
												  String query, 
												  List<Object> args, 
												  int pageNum)
	{
		ReturnList<Comment> ret = new ReturnList<Comment>();
		PreparedStatement stmt = null;
		
		try
		{
			int numResults = DEFAULT_NUM_COMMENTS;
			query = query + " LIMIT ? OFFSET ?";
			args.add(new Integer(numResults + 1));
			args.add(new Integer(pageNum * numResults));
			
			// Get results
			stmt = DataAccess.query(hndl, query, args);
			ResultSet rs = stmt.executeQuery();
			for(int i = 0; i < numResults && rs.next(); i++)
			{
				ret.list.add(Comment.getComment(rs.getInt("id")));
			}
			
			// Set if there are more results
			if(rs.next())
			{
				ret.bMore = true;
			}
			
			rs.close();
			DataAccess.pool.freeConnection(stmt.getConnection());
		}catch(Exception e)
		{
			Log.log(e, Log.NONFATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return ret;
	}
	
	/* helper function to process comment results */
	public static ReturnList<User> getUsers(StatementHandle hndl, 
												  String query, 
												  List<Object> args, 
												  int pageNum)
	{
		ReturnList<User> ret = new ReturnList<User>();
		PreparedStatement stmt = null;
		
		try
		{
			int numResults = DEFAULT_NUM_USERS;
			query = query + " LIMIT ? OFFSET ?";
			args.add(new Integer(numResults + 1));
			args.add(new Integer(pageNum * numResults));

			// Get results
			stmt = DataAccess.query(hndl, query, args);
			ResultSet rs = stmt.executeQuery();
			for(int i = 0; i < numResults && rs.next(); i++)
			{
				ret.list.add(User.getUser(rs.getInt("uid")));
			}
			
			// Set if there are more results
			if(rs.next())
			{
				ret.bMore = true;
			}
			
			rs.close();
			DataAccess.pool.freeConnection(stmt.getConnection());
		}catch(Exception e)
		{
			Log.log(e, Log.NONFATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return ret;
	}
	
	/*************************************************************************
	 * Test driver
	 *************************************************************************/
	public static void main(String args[])
	{
		ReturnList<Solution> slist = getNewestSolutions(2, 1);
		System.out.println(slist.list.size());
		
		slist = getBestSolutions(4, 0);
		System.out.println(slist.list.size());
		
		int uid = getUid("user3");
		System.out.println("uid = " + uid);		
	}
}
