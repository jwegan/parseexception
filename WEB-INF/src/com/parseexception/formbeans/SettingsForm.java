package com.parseexception.formbeans;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

import com.parseexception.StringProcessor;

public class SettingsForm extends ActionForm {
	private static final long serialVersionUID = 3961519163261382767L;
	
	// Setting Fields
	String username = "";
	String email = "";
	String about = "";
	String website = "";
    
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
        
        // Check email
        if(email.length() > 0 && !StringProcessor.verifyEmailAddress(email))
        {
        	errors.add("error", new ActionMessage("errors.invalid", "Email address"));
        }
        
        // Check username
        if(username.length() < 3 || !username.matches("^[a-zA-Z]\\w+$"))
        {
        	errors.add("error", new ActionMessage("settings.baduname"));
        }
         
        return errors;
    }
    
    
	/************************************************************************************
	 * Getter & Setter methods for fields from the settings table
	 ************************************************************************************/
    /* email */
    public String getEmail()
    {
    	return email;
    }
    
    public void setEmail(String s)
    {
    	email = s.trim();
    }
    
    /* about */
    public String getAbout()
    {
    	return about;
    }
    
    public void setAbout(String s)
    {
    	about = s.trim();
    }
    
    /* website */
    public String getWebsite()
    {
    	return website;
    }
    
    public void setWebsite(String s)
    {
    	website = s.trim();
    }
    
	/************************************************************************************
	 * Getter & Setter methods for fields from the user table
	 ************************************************************************************/
    /* username */
    public String getUsername()
    {
    	return username;
    }
    
    public void setUsername(String s)
    {
    	username = s.trim();
    }
}
