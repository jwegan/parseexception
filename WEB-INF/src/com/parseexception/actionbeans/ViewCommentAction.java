package com.parseexception.actionbeans;

import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.displaybeans.*;
import com.parseexception.model.*;

public class ViewCommentAction extends BaseAction {

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
		 verifyLogin(request, response, false);
		 
		 // Check parameters
		 String idString =  request.getParameter("id");
		 int id;
		 try
		 {
			 id = Integer.parseInt(idString);
		 }catch(Exception e)
		 {
			 ActionErrors errors = new ActionErrors();
			 errors.add("error", new ActionMessage("errors.badreq"));
			 saveErrors(request, errors);
			 return mapping.findForward("failure");
		 }
		 
		 // Add user bean to request
		 Comment c = Comment.getComment(id);
		 
		 // Add list of users comments
		 ArrayList<CommentBean> comments = new ArrayList<CommentBean>();
		 List<Comment> cList = c.getThread();
		 for(int i = 0; i < cList.size(); i++)
		 {
			 comments.add(new CommentBean(cList.get(i), curUser));
		 }
		 request.setAttribute("thread", comments);
	 
		 
		 // Forward to success
		 return mapping.findForward("success");  
	 }
}
