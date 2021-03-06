package com.parseexception.actionbeans;


import javax.servlet.http.*;
import org.apache.struts.action.*;

public class RemoveFriendAction extends BaseAction {
	public RemoveFriendAction()
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
		 // Verify login
		 if(!verifyLogin(request, response, true))
		 {
	 		 return mapping.findForward("login");
		 }
		 
		 // Check parameters
		 String friendidString =  request.getParameter("uid");
		 int friendid;
		 try
		 {
			 friendid = Integer.parseInt(friendidString);
		 }catch(Exception e)
		 {
			 ActionErrors errors = new ActionErrors();
			 errors.add("error", new ActionMessage("errors.badreq"));
			 saveErrors(request, errors);
			 return mapping.findForward("failure");
		 }
		 
		 // Add as friend
		 curUser.removeFriend(friendid);
		 
		 // Forward to success
		 return mapping.findForward("success");    		
	 }
}
