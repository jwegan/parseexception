//
// LoginForm.java
//

//
package com.parseexception.formbeans;

//
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

/*
 * 
 */
public class LoginForm extends ActionForm 
{
	private static final long serialVersionUID = -4915925268959069338L;
	
	// Fields
    private String provider = null;
    private boolean keepLoggedIn = false;

    // Methods
    /*
     * ctor()
     */
    public LoginForm()
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
        
        // Check provider
        if (provider == null || provider.length() < 3)
        {
        	errors.add("error", new ActionMessage("errors.invalid", "Provider"));
        }
        
        return errors;
    }

	/************************************************************************************
	 * Getter & Setter methods
	 ************************************************************************************/
    /* provider */
    public String getProvider() 
    {
        return provider;
    }
    
    public void setProvider(String s)
    {
    	provider = s.trim();
    }
    
    /* keepLoggedIn */
    public boolean getKeepLoggedIn()
    {
    	return keepLoggedIn;
    }
    
    public void setKeepLoggedIn(boolean b)
    {
    	keepLoggedIn = b;
    }

}

