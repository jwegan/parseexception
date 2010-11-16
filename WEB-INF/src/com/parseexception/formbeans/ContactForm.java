package com.parseexception.formbeans;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

import com.parseexception.StringProcessor;

public class ContactForm extends ActionForm {
	private static final long serialVersionUID = -7036813180568396145L;
	
	String contactType = "";
	String email = "";
	String body = "";
	
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
    	
    	if(contactType.length() == 0)
    	{
    		errors.add("error", new ActionMessage("contact.invalid", "Contact type"));
    	}
    	
    	if(body.length() < 10)
    	{
    		errors.add("error", new ActionMessage("contact.empty"));
    	}
    	
    	if(email.length() == 0 || !StringProcessor.verifyEmailAddress(email))
    	{
    		errors.add("error", new ActionMessage("contact.invalid", "Email"));
    	}
    	
    	return errors;
    }
    
	/************************************************************************************
	 * Getter & Setter methods
	 ************************************************************************************/
    /* contactType */
    public String getContactType()
    {
    	return contactType;
    }

	public void setContactType(String s)
	{
		contactType = s.trim();
	}
	
	/* email */
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String s)
	{
		email = s.trim();
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
}
