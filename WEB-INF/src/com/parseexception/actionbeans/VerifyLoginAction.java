package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;
import com.parseexception.model.*;

public class VerifyLoginAction extends BaseAction{
	 /*
	  * ctor()
	  */
	 public VerifyLoginAction() 
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
	 	// Verify
	 	String identity;
	 	try
	 	{
	 		identity = OpenIdManager.verifyResponse(request);
	 	}catch(Exception e)
	 	{
	 		ActionErrors errors = new ActionErrors();
	 		errors.add("error", new ActionMessage("errors.blank", e.getMessage()));
	 		saveErrors(request, errors);
	 		identity = null;
	 	}
	 	
	 	if (identity != null)
	 	{
	 		int uid = DBQueries.getUid(identity);
	 		User thisuser = null;
	 		if(uid <= 0)
	 		{
	 			// New user
	 			thisuser = new User(identity, OpenIdManager.getOidCookie(request));	 			
	 		}else
	 		{
	 			// Existing user
	 			thisuser = User.getUser(uid);
	 		}
	 		
	 		if(thisuser == null || thisuser.getUid() <= 0)
	 		{
	 			// Failure
		 		return mapping.findForward("failure");
	 		}
	 		
	 		int maxAge = (getCookie("keep_loggedin", request) != null)? 15811200 : -1;
 			setLoginCookie(thisuser.getUid(), maxAge, request, response);
 			DBQueries.logIP(thisuser.getUid(), request.getRemoteAddr());
 			
	        // Success
	 		String lasturl = getCookie("lasturl", request);
	 		if(lasturl != null)
	 		{
	 			ActionForward newForward = new ActionForward(lasturl);
		 		return newForward;
	 		}else{
	 			return mapping.findForward("success");
	 		}
	 	}
	 	else
	 	{
	 		// Failure
	 		return mapping.findForward("failure");
	 	}
	 }
}
