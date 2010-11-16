package com.parseexception.displaybeans;

import java.io.Serializable;

import com.parseexception.StringProcessor;
import com.parseexception.model.*;

public class CommentBean implements Serializable {
	private static final long serialVersionUID = -8481108261730493421L;
	private Comment thisComment;
	private boolean bEditable = false;
	
	/*
	 * ctor
	 */
	public CommentBean(Comment c, User u)
	{
		thisComment = c;
		
		if(u != null && c.getUid() == u.getUid())
		{
			bEditable = true;
		}else{
			bEditable = false;
		}
	}
	
	/* id */
	public String getId()
	{
		return Integer.toString(thisComment.getId());
	}
	
	/* body */
	public String getBody(){
		return StringProcessor.goingToDisplay(thisComment.getBody());
	}
	
	/* raw body */
	public String getBodyRaw()
	{
		return thisComment.getBodyRaw();
	}
	
	/* timestamp */
	public String getTimestamp(){
		return thisComment.getTimestamp();
	}
	
	/* depth */
	public int getDepth()
	{
		return thisComment.getDepth();
	}
	
	/* editted */
	public String getEditted()
	{
		return Boolean.toString(thisComment.getEditted());
	}
	
	/* username */
	public String getUsername()
	{
		User u = User.getUser(thisComment.getUid());
		return u.getUsername();
	}
	
	/* uid */
	public String getUid()
	{
		return Integer.toString(thisComment.getUid());
	}
	
	/* solutionquestion */
	public String getSolutiontitle()
	{
		Solution s = Solution.getSolution(thisComment.getSid());
		return s.getQuestion();
	}
	
	/* sid */
	public String getSid()
	{
		return Integer.toString(thisComment.getSid());
	}
	
	/* indent */
	public String getIndent()
	{
		return Integer.toString(thisComment.getDepth() * 20);
	}
	
	/* editable */
	public boolean getEditable()
	{
		return bEditable;
	}
	
	/* parentId */
	public String getParentId()
	{
		return Integer.toString(thisComment.getParent_id());
	}
}
