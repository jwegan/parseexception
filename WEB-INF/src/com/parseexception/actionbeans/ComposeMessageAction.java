package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.formbeans.*;
import com.parseexception.model.*;

public class ComposeMessageAction extends BaseAction {
	public ComposeMessageAction()
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
		 
		 // Check to id
		 MessageForm mform = (MessageForm) form;
		 String toidString = request.getParameter("uid");
		 String toname;
		 int msgid = 0;
		 try
		 {
			 if(toidString != null)
			 {
				 User u = User.getUser(Integer.parseInt(toidString));
				 toname = u.getUsername();
				 mform.setToName(toname);
			 }
			 
			 if(request.getParameter("id") != null)
				 msgid = Integer.parseInt(request.getParameter("id"));
		 }catch(Exception e)
		 {
			 ActionErrors errors = new ActionErrors();
			 errors.add("error", new ActionMessage("errors.badreq"));
			 saveErrors(request, errors);
			 return mapping.findForward("failure");
		 }

		 // Populate form bean
		 if(msgid > 0)
		 {
			 Message m = Message.getMessage(msgid);
			 mform.setSubject("RE:" + m.getSubject());
			 mform.setThreadid(m.getThreadid());
			 User u = User.getUser(m.getFrom_id());
			 mform.setToName(u.getUsername());
		 }

		 // Forward to success
		 return mapping.findForward("success");    		
	 }	
}
