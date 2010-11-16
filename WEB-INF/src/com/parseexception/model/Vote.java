package com.parseexception.model;

import java.util.ArrayList;
import java.io.*;

public class Vote implements Serializable{
	private static final long serialVersionUID = 5215986602058257218L;
	
	private int uid;
	private int sid;
	private int vote;
	
	static
	{
		get_vote = new StatementHandle();
		set_vote = new StatementHandle();
		insert_vote = new StatementHandle();
	}
	
	private static transient StatementHandle get_vote;
	public Vote(int uid, int sid)
	{
		this.uid = uid;
		this.sid = sid;
		
		String query = "SELECT upvote FROM votes WHERE uid = ? AND sid = ?";
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(uid));
		args.add(new Integer(sid));
		vote = DataAccess.queryReturnInt(get_vote, query, args, "upvote");
		
		ObjectCache cache = ObjectCache.getInstance();
		cache.addObject(this, uid, sid, "votes");
	}
	
	public static Vote getVote(int uid, int sid)
	{
		ObjectCache cache = ObjectCache.getInstance();
		Vote v = (Vote) cache.lookupObject(uid, sid, "votes");
		if(v == null)
		{
			v = new Vote(uid, sid);
		}
		
		return v;
	}
	
	public int getUpvote()
	{
		return vote;
	}
	
	private static transient StatementHandle set_vote;
	private static transient StatementHandle insert_vote;
	public boolean setUpvote(int v)
	{
		StatementHandle hndl;
		String query;
		
		if(vote == 0)
		{
			query = "INSERT INTO votes (upvote, uid, sid) VALUES (?, ?, ?)";
			hndl = insert_vote;
		}else
		{
			query = "UPDATE votes SET upvote = ? WHERE uid = ? AND sid = ?";
			hndl = set_vote;
		}
		
		ArrayList<Object> args = new ArrayList<Object>();
		args.add(new Integer(v));
		args.add(new Integer(uid));
		args.add(new Integer(sid));
		
		boolean ret;
		if(vote == 0)
			ret = DataAccess.createEntry(hndl, query, "id", args) > 0;
		else
			ret = DataAccess.update(hndl, query, args);
		if(ret)
		{
			vote = v;
			Solution s = Solution.getSolution(sid);
			s.setScore();
		}
		
		return ret;
	}
}
