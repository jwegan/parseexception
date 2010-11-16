package com.parseexception.model;

import java.io.Serializable;
import java.sql.*;
import java.util.*;

import com.parseexception.Config;
import com.parseexception.Log;

public class Solution implements Serializable {
	/************************************************************************************
	 * Initialization
	 ************************************************************************************/
	private static final long serialVersionUID = -6718768160354421393L;
	private int sid = -1;
	
	private Column submitter;
	private Column question;
	private Column answer;
	private Column answer_raw;
	private Column timestamp;
	private Column score;
	private Column ispublic;
	
	/* Static initialization block */
	static
	{
		createSolutionStmt = new StatementHandle();
		populate_answer = new StatementHandle();
		
		set_submitter = new StatementHandle();
		set_question = new StatementHandle();
		set_answer = new StatementHandle();
		set_answer_raw = new StatementHandle();
		set_timestamp = new StatementHandle();
		set_score = new StatementHandle();
		set_ispublic = new StatementHandle();
		
		get_score = new StatementHandle();
		
		get_comments = new StatementHandle();
		delete_comments = new StatementHandle();
		delete_answer = new StatementHandle();
		
		get_upvote_count = new StatementHandle();
		get_downvote_count = new StatementHandle();
	}
	
	/* 
	 * Method Name: Answer
	 * Description: Creates a new answer in the database
	 */
	private static transient StatementHandle createSolutionStmt;
	public Solution(
					int submitter,
					String question,
					String answer,
					String answer_raw,
					boolean isPublic)
	{
		try
		{
			String query = "INSERT INTO solutions (submitter, question, answer, answer_raw, ispublic) " +
						   "VALUES (?, ?, ?, ?, ?)";
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(new Integer(submitter));
			args.add(question);
			args.add(answer);
			args.add(answer_raw);
			args.add(new Boolean(isPublic));
			
			sid = DataAccess.createEntry(createSolutionStmt, query, "sid", args);
			if(sid >= 0){
				Log.log("Created new solution: " + question, Log.DEBUG);
				populateAnswer();
			}else{
				Log.log("Unable to create solution: " + question, Log.NONFATAL_ERROR);
			}
		}catch(Exception e){
			Log.log(e, Log.FATAL_ERROR);
		}
	}
	
	/*
	 * Method Name: Answer
	 * Description: Creates an instance of an existing answer
	 */
	private Solution(int sid)
	{
		this.sid = sid;
		populateAnswer();
	}
	
	private static transient StatementHandle populate_answer;
	private void populateAnswer()
	{
		String query = "SELECT * FROM solutions WHERE sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(sid));
		
		submitter = new Column("submitter", Column.INTEGER);
		question = new Column("question", Column.STRING);
		answer = new Column("answer", Column.STRING);
		answer_raw = new Column("answer_raw", Column.STRING);
		timestamp = new Column("timestamp", Column.TIMESTAMP);
		score = new Column("score", Column.INTEGER);
		ispublic = new Column("ispublic", Column.BOOLEAN);
			
		ArrayList<Column> columns = new ArrayList<Column>();
		columns.add(submitter);
		columns.add(question);
		columns.add(answer);
		columns.add(answer_raw);
		columns.add(timestamp);
		columns.add(score);
		columns.add(ispublic);
		
		DataAccess.queryReturnColumns(populate_answer, query, args, columns);
		
		// Add to cache
		ObjectCache cache = ObjectCache.getInstance();
		cache.addObject(this, sid, "solution");
	}
	
	public static Solution getSolution(int sid)
	{
		ObjectCache cache = ObjectCache.getInstance();
		Solution s = (Solution) cache.lookupObject(sid, "solution");
		if(s != null)
		{
			return s;
		}else
		{
			s = new Solution(sid);
			return s;
		}
	}
	/************************************************************************************
	 * Getter & Setter methods for answer fields 
	 ************************************************************************************/
	/* sid */
	public int getSid()
	{
		return sid;
	}
	
	public boolean setSid()
	{
		return false;
	}
	
	/* submitter */
	public int getSubmitter()
	{
		return submitter.getInt();
	}
	
	private static transient StatementHandle set_submitter;
	public boolean setSubmitter(int uid)
	{
		String query = "UPDATE solutions SET submitter = ? WHERE sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		args.add(new Integer(sid));
		boolean ret = DataAccess.update(set_submitter, query, args);
		
		if(ret)
		{
			this.submitter.setInt(uid);
		}
		return ret;
	}
	
	/* question */
	public String getQuestion()
	{
		return question.getString();
	}
	
	private static transient StatementHandle set_question;
	public boolean setQuestion(String question)
	{
		String query = "UPDATE solutions SET question = ? WHERE sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(question);
		args.add(new Integer(sid));
		boolean ret = DataAccess.update(set_question, query, args);
		
		if(ret)
		{
			this.question.setString(question);
		}
		return ret;
	}
	
	/* answer */
	public String getAnswer()
	{
		return answer.getString();
	}
	
	private static transient StatementHandle set_answer;
	public boolean setAnswer(String answer)
	{
		String query = "UPDATE solutions SET answer = ? WHERE sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(answer);
		args.add(new Integer(sid));
		boolean ret = DataAccess.update(set_answer, query, args);
		
		if(ret)
		{
			this.answer.setString(answer);
		}
		return ret;
	}
	
	/* answer_raw */
	public String getAnswerRaw()
	{
		return answer_raw.getString();
	}
	
	private static transient StatementHandle set_answer_raw;
	public boolean setAnswerRaw(String answer_raw)
	{
		String query = "UPDATE solutions SET answer_raw = ? WHERE sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(answer_raw);
		args.add(new Integer(sid));
		boolean ret = DataAccess.update(set_answer_raw, query, args);
		
		if(ret)
		{
			this.answer_raw.setString(answer_raw);
		}
		return ret;
	}
	
	/* timestamp */
	public String getTimestamp()
	{
		return timestamp.getTimestamp();
	}
	
	private static transient StatementHandle set_timestamp;
	public boolean setTimestamp(java.util.Date d)
	{
		String query = "UPDATE solutions SET timestamp = ? WHERE sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(d);
		args.add(new Integer(sid));
		boolean ret = DataAccess.update(set_timestamp, query, args);
		
		if(ret)
		{
			this.timestamp.setTimestamp(d);
		}
		return ret;
	}
	
	/* score */
	public int getScore()
	{
		return score.getInt();
	}
	
	private static transient StatementHandle set_score;
	private static transient StatementHandle get_score;
	public synchronized boolean setScore()
	{
		String query = "UPDATE solutions SET score = " +
					   "(SELECT COUNT(DISTINCT uid) FROM votes WHERE sid = ?) " +
					   "WHERE sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(sid));
		args.add(new Integer(sid));
		DataAccess.update(set_score, query, args);
		
		query = "SELECT score FROM solutions WHERE sid = ?";
		args.clear();
		args.add(new Integer(sid));
		int votecnt = DataAccess.queryReturnInt(get_score, query, args, "score");
		this.score.setInt(votecnt);
		
		return true;
	}
	
	/* ispublic */
	public boolean getIsPublic()
	{
		return ispublic.getBool();
	}
	
	private static transient StatementHandle set_ispublic;
	public boolean setIsPublic(boolean b)
	{
		String query = "UPDATE solutions SET ispublic = ? WHERE sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Boolean(b));
		args.add(new Integer(sid));
		boolean ret = DataAccess.update(set_ispublic, query, args);
		
		if(ret)
		{
			this.ispublic.setBool(b);
		}
		return ret;
	}

	/************************************************************************************
	 * Advanced operations
	 ************************************************************************************/
	/* delete answer */
	private static transient StatementHandle delete_comments;
	private static transient StatementHandle delete_answer;
	public boolean delete()
	{
		boolean retval;
		
		String query = "DELETE FROM comments WHERE sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(sid));
		retval = DataAccess.update(delete_comments, query, args);
		
		query = " DELETE FROM solutions WHERE sid = ?";
		retval &= DataAccess.update(delete_answer, query, args);
		
		if(retval)
		{
			Log.log("Succesfully deleted answer: " + sid, Log.DEBUG);
		}else{
			Log.log("Unable to delete answer: " + sid, Log.NONFATAL_ERROR);
		}
		
		return retval;
	}
	
	/* tags */
	public String getTagsAsString()
	{
		TagList tags = TagList.getTagList(sid);
		List<String> list = tags.getTags();
		StringBuilder retstr = new StringBuilder("");
		
		for(int i = 0; i < list.size(); i++)
		{
			retstr.append(list.get(i));
			if(i < (list.size() - 1))
				retstr.append(',');
		}
		
		return retstr.toString();
	}
	
	public List<String> getTags()
	{
		TagList list = TagList.getTagList(sid);
		return list.getTags();
	}
	
	public boolean setTags(String tagstr)
	{
		TagList list = TagList.getTagList(sid);
		return list.setSolutionTags(tagstr, this.getSubmitter(), this.getSid());
	}

	/* comments */
	private static transient StatementHandle get_comments;
	@SuppressWarnings("unchecked")
	public List<Comment> getComments()
	{
		ArrayList<Comment> comments = new ArrayList<Comment>();
		PreparedStatement stmt = null;
		
		try
		{
			ObjectCache cache = ObjectCache.getInstance();
			ReturnList<Comment> results = (ReturnList<Comment>) cache.lookupObject(new Integer(sid), "story_comments");
			if(results != null && ((new java.util.Date().getTime()) - results.creationTime) > Config.cacheTimeout_ms)
			{
				return results.list;
			}
			
			String query = "SELECT id FROM comments WHERE sid = ? ORDER BY path ASC, id ASC";
			ArrayList<Object> args = new ArrayList<Object>();
			args.add(new Integer(sid));
			
			stmt = DataAccess.query(get_comments, query, args);
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				comments.add(Comment.getComment(rs.getInt("id")));
			}
			rs.close();
			DataAccess.pool.freeConnection(stmt.getConnection());
			
			results = new ReturnList<Comment>();
			results.list = comments;
			cache.addObject(results, new Integer(sid), "story_comments");
		}
		catch(Exception e)
		{
			Log.log(e, Log.FATAL_ERROR);
			DataAccess.pool.garbageCollect(stmt);
		}
		
		return comments;
	}
	
	/* votes */
	private static transient StatementHandle get_upvote_count;
	public int getUpvoteCount()
	{
		String query = "SELECT COUNT(DISTINCT uid) AS count FROM votes " +
					   "WHERE sid = ? AND upvote = 1";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(sid));
		return DataAccess.queryReturnInt(get_upvote_count, query, args, "count");
	}
	
	private static transient StatementHandle get_downvote_count;
	public int getDownvoteCount()
	{
		String query = "SELECT COUNT(DISTINCT uid) AS count FROM votes " +
					   "WHERE sid = ? AND upvote = 2";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(sid));
		return DataAccess.queryReturnInt(get_downvote_count, query, args, "count");
	}
	
	/************************************************************************************
	 * Test Driver
	 ************************************************************************************/
	public static void main(String[] args) {
		/* Test creating a answer */
		Solution s;
		/*
		s = new Answer(2, "", "");
		s.setSubmitter(1);
		s.setQuestion("Working Question");
		s.setAnswer("Here's the scoop ;-P");
		
		System.out.println("sid: " + s.getSid());
		System.out.println("submitter: " + s.getSubmitter());
		System.out.println("question: " + s.getQuestion());
		System.out.println("answer: " + s.getAnswer());
		System.out.println("timestamp: " + s.getTimestamp());
		*/
		
		/* Test getting comments */
		s = new Solution(2);
		User u = User.getUser(s.getSubmitter());
		System.out.println("submitter: " + u.getUsername());
		List<Comment> thread = s.getComments();
		for(int i = 0; i < thread.size(); i++)
		{
			Comment c = thread.get(i);
			for(int j = 0; j < c.getDepth(); j++)
				System.out.print("\t");
			System.out.println(c.getBody());
		}
		
		/* Test tag system */
		System.out.println();
		String tagstr = " inspiring , how this site started,  jegan ";
		s.setTags(tagstr);
		List<String> tags = s.getTags();
		for(int i = 0; i < tags.size(); i++)
		{
			System.out.println(tags.get(i));
		}
	}
}
