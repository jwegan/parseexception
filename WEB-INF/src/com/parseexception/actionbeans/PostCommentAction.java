package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.SpamFilter;
import com.parseexception.StringProcessor;
import com.parseexception.displaybeans.CommentBean;
import com.parseexception.formbeans.CommentForm;
import com.parseexception.model.Comment;

public class PostCommentAction extends BaseAction {
	public PostCommentAction()
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
	 		 return mapping.findForward("failure");
		 }
	 	
		 // Process Form
		 CommentForm cForm = (CommentForm) form;
		 Comment c;
		 if(cForm.getId() > 0)
		 {
			 c = Comment.getComment(cForm.getId());
			 c.setBody(StringProcessor.goingToDB(cForm.getBody()));
			 c.setBodyRaw(cForm.getBody());
			 c.setEditted(true);
		 }else if(!SpamFilter.isSpam(curUser, cForm.getBody(), request.getRemoteAddr())){
			 String pfx = null;
			 if(cForm.getParentId() > 0)
			 {
				 Comment parent = Comment.getComment(cForm.getParentId());
				 pfx = parent.getPath();
			 }
			 
			 c = new Comment(curUser.getUid(), 
					 		 cForm.getSid(),
					 		 StringProcessor.goingToDB(cForm.getBody()),
					 		 cForm.getBody(),
					 		 pfx);
		 }else
		 {
			 return mapping.findForward("failure");
		 }

		 // Forward
		 CommentBean bean = new CommentBean(c, curUser);
		 request.setAttribute("comment", bean);
		 return mapping.findForward("success");    		
	 }	
}
