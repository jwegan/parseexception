package com.parseexception.actionbeans;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.parseexception.formbeans.*;
import com.parseexception.model.*;

/*
* 
*/
public class LoginAction extends BaseAction 
{
	 /*
	  * ctor()
	  */
	 public LoginAction() 
	 {
	     super();
	 }

	 /**
	  * Process the request and return an <code>ActionForward</code> instance
	  * describing where and how control should be forwarded, or
	  * <code>null</code>if the response has already been completed.
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  *
	  * @exception Exception if the application logic throws an exception
	  *
	  * @return the ActionForward for the next view
	  */
	 public ActionForward execute (
	     ActionMapping mapping,
	     ActionForm form,
	     HttpServletRequest request,
	     HttpServletResponse response)
	     throws Exception 
	 {
	 	LoginForm loginForm = (LoginForm)form;
	
	 	// Validate form
	 	ActionErrors errors = loginForm.validate(mapping, request);
	 	if (errors.size() > 0)
	 	{
	     	saveErrors(request, errors);
	 		return mapping.findForward("failure");
	 	}
	 	
	 	// Get provider
	 	String provider = loginForm.getProvider();
	 	ActionForward oid_forward;
	 	try
	 	{
	 		oid_forward = OpenIdManager.authRequest(provider, request, response);
	 	}catch(Exception e)
	 	{
	 		errors.add("error", new ActionMessage("errors.blank", e.getMessage()));
	 		saveErrors(request, errors);
	 		oid_forward = null;
	 	}
	 	
	 	if (oid_forward != null)
	 	{
	 		if(loginForm.getKeepLoggedIn())
	 		{
	 			Cookie ck = new Cookie("keep_loggedin", "true");
	 			ck.setMaxAge(-1);
	 			response.addCookie(ck);
	 		}
	 		return oid_forward;
	 	}
	 	else
	 	{
	 		// Failure
	 		return mapping.findForward("failure");
	 	}
	 }
}

