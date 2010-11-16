package com.parseexception.formbeans;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class MessageForm extends ActionForm {
	private static final long serialVersionUID = 4218317013467071828L;
	
	// Fields
	String toName = "";
	String subject = "";
	String body = "";
	int threadId = 0;
	
	/*
	 * ctor
	 */
	public MessageForm()
	{
		super();
	}
	
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
        
        // Check toName
        if (toName.length() < 3 || toName.length() > 50)
        {
        	String errstr = (toName.length() != 0)? "message.invalid" :
        											"message.empty";
        	errors.add("error", new ActionMessage(errstr, "Username"));
        	
        }if(toName.length() <= 0)
        {
        	errors.add("error", new ActionMessage("message.nouser", toName));
        }
        
        // Check subject
        if(subject.length() > 255)
        {
        	errors.add("error", new ActionMessage("message.invalid", "Subject"));
        }
        
        // Check subject not empty
        if(subject.length() == 0)
        {
        	errors.add("error", new ActionMessage("message.empty", "Subject"));
        }
        
        // Check body not empty
        if(body.length() == 0)
        {
        	errors.add("error", new ActionMessage("message.empty", "Message"));
        }
        
        return errors;
    }

	/************************************************************************************
	 * Getter & Setter methods
	 ************************************************************************************/
    /* toName */
    public String getToName()
    {
    	return toName;
    }
    
    public void setToName(String s)
    {
    	toName = s.trim();
    }
    
    /* subject */
    public String getSubject()
    {
    	return subject;
    }
    
    public void setSubject(String s)
    {
    	subject = s.trim();
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
    
    /* threadid */
    public int getThreadid()
    {
    	return threadId;
    }
    
    public void setThreadid(int i)
    {
    	threadId = i;
    }
    
}
