package com.parseexception.actionbeans;

import java.util.ArrayList;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.displaybeans.*;
import com.parseexception.model.*;

public class ViewMessageAction extends BaseAction {

	public ViewMessageAction()
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
		 String idString =  request.getParameter("id");
		 int id;
		 int pageNum = 0;
		 try
		 {
			 id = Integer.parseInt(idString);
			 
			 if(request.getParameter("pageNum") != null)
				 pageNum = Integer.parseInt(request.getParameter("pageNum"));
		 }catch(Exception e)
		 {
			 ActionErrors errors = new ActionErrors();
			 errors.add("error", new ActionMessage("errors.badreq"));
			 saveErrors(request, errors);
			 return mapping.findForward("failure");
		 }
		 
		 if(id <= 0)
		 {
			 request.setAttribute("subject", "View Messages");
			 return mapping.findForward("success");
		 }else{
			 ComposeMessageAction subAction = new ComposeMessageAction();
			 subAction.execute(mapping, form, request, response);
		 }
		 
		 // Get message & mark as read if necessary
		 Message msg = Message.getMessage(id);
		 if(curUser.getUid() == msg.getTo_id())
			 msg.setUnread(false);
		 
		 // Populate display bean
		 ReturnList<Message> results = msg.getThread(pageNum);
		 ArrayList<MessageBean> messages = new ArrayList<MessageBean>();
		 for(int i = 0; i < results.list.size(); i++)
		 {
			 messages.add(new MessageBean(results.list.get(i)));
		 }
		 request.setAttribute("messages", messages);
		 request.setAttribute("selectedmsg", msg);
		 
		 // Set next prev
		 setNextPrev(request, pageNum, results);
		 request.setAttribute("actionURL", "/viewmessage?id=" + id);

		 // Forward to success
		 return mapping.findForward("success");    		
	 }
}
