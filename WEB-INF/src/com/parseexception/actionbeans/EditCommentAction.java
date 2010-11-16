package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.formbeans.*;
import com.parseexception.model.*;

public class EditCommentAction extends BaseAction {
	public EditCommentAction()
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
		 String sidString =  request.getParameter("sid");
		 String idString = request.getParameter("id");
		 String pidString = request.getParameter("pid");
		 int sid = 0, id = 0, pid = 0;
		 try
		 {	 if(sidString != null)
			 	 sid = Integer.parseInt(sidString);
			 
			 if(idString != null)
				 id = Integer.parseInt(idString);
			 
			 if(pidString != null)
				 pid = Integer.parseInt(pidString);
			 
			 if(sid <= 0 && id <= 0)
				 throw new Exception("Bad Reqest");
		 }catch(Exception e)
		 {
			 ActionErrors errors = new ActionErrors();
			 errors.add("error", new ActionMessage("errors.badreq"));
			 saveErrors(request, errors);
			 return mapping.findForward("failure");
		 }
		 
		 // Populate form
		 CommentForm cForm = (CommentForm) form;
		 if(id > 0)
		 {
			 Comment c = Comment.getComment(id);
			 cForm.setBody(c.getBodyRaw());
			 cForm.setParentId(c.getParent_id());
			 cForm.setSid(c.getSid());
			 cForm.setId(c.getId());
		 }else{
			 cForm.setSid(sid);
			 cForm.setParentId(pid);
		 }		 

		 // Forward to success
		 return mapping.findForward("success");    		
	 }	
}
