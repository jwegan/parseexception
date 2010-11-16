package com.parseexception.displaybeans;

import java.io.Serializable;
import java.util.*;

import com.parseexception.StringProcessor;
import com.parseexception.model.*;

public class SolutionBean implements Serializable {
	private static final long serialVersionUID = 1331666216388231360L;
	private Solution thisSolution;
	private int vote;
	
	/* ctor */
	public SolutionBean(Solution s, int vote)
	{
		thisSolution = s;
		this.vote = vote;
	}

	/* sid */
	public String getSid()
	{
		return Integer.toString(thisSolution.getSid());
	}
	
	/* uname */
	public String getUname()
	{
		User u = User.getUser(thisSolution.getSubmitter());
		return u.getUsername();
	}
	
	/* uid */
	public String getUid()
	{
		return Integer.toString(thisSolution.getSubmitter());
	}
	
	/* question */
	public String getQuestion()
	{
		return thisSolution.getQuestion();
	}
	
	/* Answer */
	public String getAnswer()
	{
		return StringProcessor.goingToDisplay(thisSolution.getAnswer());
	}
	
	/* timestamp */
	public String getTimestamp()
	{
		return thisSolution.getTimestamp();
	}
	
	/* score */
	public String getScore()
	{
		return Integer.toString(thisSolution.getScore());
	}
	
	/* UpvoteCount */
	public String getUpoteCount()
	{
		return Integer.toString(thisSolution.getUpvoteCount());
	}
	
	/* DownCount */
	public String getDownVoteCount()
	{
		return Integer.toString(thisSolution.getDownvoteCount());
	}
	
	/* list of tags */
	public List<String> getTags()
	{
		return thisSolution.getTags();
	}
	
	/* userVote - Returns the vote of the currently logged in user */
	public int getUserVote()
	{
		return this.vote;
	}
}
