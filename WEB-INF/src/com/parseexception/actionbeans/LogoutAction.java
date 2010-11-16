package com.parseexception.actionbeans;


import javax.servlet.http.*;
import org.apache.struts.action.*;

public class LogoutAction extends BaseAction {
	/*
	 * ctor
	 */
	public LogoutAction()
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
		// Invalidate session
		HttpSession session = request.getSession(true);
		session.invalidate();
    	
		deleteLoginCookie(request, response);
    	
    	return mapping.findForward("success");
	 }
}
