package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.formbeans.*;
import com.parseexception.model.*;

public class SendMessageAction extends BaseAction {
	public SendMessageAction()
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
	 	
	 	// Validate form
   		MessageForm messageForm = (MessageForm)form;
	 	ActionErrors errors = messageForm.validate(mapping, request);
	 	if (errors.size() > 0)
	 	{
	     	saveErrors(request, errors);
	 		return mapping.findForward("failure");
	 	}
	 	
	 	// Process Form
	 	int toid = DBQueries.getUid(messageForm.getToName());
	 	if(toid <= 0)
	 	{
	 		errors.add("error", new ActionMessage("message.nouser", messageForm.getToName()));
	 		saveErrors(request, errors);
	 		return mapping.findForward("failure");
	 	}
	 	
	 	// Create message
	 	Message m = new Message(curUser.getUid(), toid, 
	 				messageForm.getSubject(), messageForm.getBody(), 
	 				messageForm.getThreadid());
	 	
	 	// Forward
	 	if (m.getId() > 0)
	 	{
	 		// Reset form
	     	messageForm.reset(mapping, request);
	     	
	        // Forward to success
	        return mapping.findForward("success");    		
	 	}
	 	else
	 	{
	 		// Add error
	 		errors.add("error", new ActionMessage("message.failure"));
	 		saveErrors(request, errors);
	 		
	 		// Forward to failure
	 		return mapping.findForward("failure");
	 	}
	 }	
}
