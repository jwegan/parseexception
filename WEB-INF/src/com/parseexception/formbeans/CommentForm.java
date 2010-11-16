package com.parseexception.formbeans;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class CommentForm extends ActionForm {
	private static final long serialVersionUID = 7027923467253357196L;
	
	// Fields
	private int sid = 0;
	private int id = 0;
	private int parent_id;
	private String body = "";
    
    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionMessages</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionMessages</code> object with no
     * recorded error messages.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     *
     * @return ActionMessages if any validation errors occurred
     */
    public ActionErrors validate (
        ActionMapping mapping,
        HttpServletRequest request) 
    {
        ActionErrors errors = new ActionErrors();
        
        // Check body
        if(body == null || body.length() <= 0)
        {
        	errors.add("error", new ActionMessage("comment.invalid", "Comment body"));
        }
         
        return errors;
    }
    
    
	/************************************************************************************
	 * Getter & Setter methods
	 ************************************************************************************/
    /* sid */
    public int getSid()
    {
    	return sid;
    }
    
    public void setSid(int i)
    {
    	sid = i;
    }
    
    /* parentId */
    public int getParentId()
    {
    	return parent_id;
    }
    
    public void setParentId(int i)
    {
    	parent_id = i;
    }
    
    /* body */
    public String getBody()
    {
    	return body;
    }
    
    public void setBody(String s)
    {
    	body = s.trim();
    }
    
    /* isEdit */
    public int getId()
    {
    	return id;
    }
    
    public void setId(int i)
    {
    	id = i;
    }
}
