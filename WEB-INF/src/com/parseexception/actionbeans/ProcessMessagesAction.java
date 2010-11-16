package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.Log;
import com.parseexception.formbeans.*;
import com.parseexception.model.Message;

public class ProcessMessagesAction extends BaseAction {
	public ProcessMessagesAction()
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
		 if (!verifyLogin(request, response, true))
		 {
			 return mapping.findForward("login");
		 }
		 
		 
		 try
		 {
			 String messages[];
			 String action;
			 if(request.getParameter("id") != null &&
				request.getParameter("action") != null)
			 {
				 messages = new String[1];
				 messages[0] = request.getParameter("id");
				 action = request.getParameter("action");
			 }else{
				 MessageBoxForm mbForm = (MessageBoxForm) form;
				 messages = mbForm.getSelectedItems();
				 action = mbForm.getAction();
			 }
			 
			 for(int i = 0; i < messages.length; i++)
			 {
				Message msg = Message.getMessage(Integer.parseInt(messages[i]));
				
				// Verify action is being done by person who received msg
				if(msg.getTo_id() != curUser.getUid())
					continue;
				
				if(action.compareTo("markasread") == 0)
				{
					msg.setUnread(false);
				}else if(action.compareTo("markasunread") == 0)
				{
					msg.setUnread(true);
				}else if(action.compareTo("delete") == 0)
				{
					msg.setDeleted(true);
				}
			 }
		 }catch(Exception e)
		 {
			 Log.log(e, Log.NONFATAL_ERROR);
		 }

		 // Forward to success
		 return mapping.findForward("success");    		
	 }	
}
