package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.Log;
import com.parseexception.displaybeans.MessageBean;
import com.parseexception.model.*;

import java.util.*;

public class ViewOutboxAction extends BaseAction {
	public ViewOutboxAction()
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
	   	
	   	// Get parameters
	   	int pageNum = 0;
	   	try
	   	{
	   		if(request.getParameter("pageNum") != null)
	   			pageNum = Integer.parseInt(request.getParameter("pageNum"));
	   	}catch(Exception e)
	   	{
	   		Log.log(e, Log.FATAL_ERROR);
	   	}
	 	
	 	// Put messages into display beans
	 	ReturnList<Message> results = curUser.getSentMessages(pageNum);
	 	List<Message> mlist = results.list;
	 	ArrayList<MessageBean> blist = new ArrayList<MessageBean>();
	 	for(int i = 0; i < mlist.size(); i++)
	 	{
	 		blist.add(new MessageBean(mlist.get(i)));
	 	}
	 	
	 	// Set next prev
	 	setNextPrev(request, pageNum, results);
	 	request.setAttribute("actionURL", "/viewinbox");
	 	
	    // Forward to success
	 	request.setAttribute("messages", blist);
	    return mapping.findForward("success");    		
	 }
}
