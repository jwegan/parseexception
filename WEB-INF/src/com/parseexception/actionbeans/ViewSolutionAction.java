package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.displaybeans.*;
import com.parseexception.model.*;

import java.util.*;

public class ViewSolutionAction extends BaseAction {

	public ViewSolutionAction()
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
		 // Get login if user is logged in
		 verifyLogin(request, response, false);
		 
		 // Check parameters
		 String sidString =  request.getParameter("sid");
		 int sid = 0;
		 try
		 {
			 if(sidString != null && sidString.compareTo("0") != 0)
			 {
				 sid = Integer.parseInt(sidString);
			 }else
			 {
				 Integer sidInt = (Integer) request.getAttribute("sid");
				 sid = sidInt.intValue();
			 }
		 }catch(Exception e)
		 {
			 ActionErrors errors = new ActionErrors();
			 errors.add("error", new ActionMessage("errors.badreq"));
			 saveErrors(request, errors);
			 return mapping.findForward("failure");
		 }
		 
		 // Get display bean
		 SolutionBean sbean;
		 Solution s = Solution.getSolution(sid);
		 if(curUser != null)
		 {
			 sbean = new SolutionBean(s, curUser.getVote(sid));
		 }else{
			 sbean = new SolutionBean(s, 0);
		 }
		 request.setAttribute("solution", sbean);
		 
		 // Get comment Thread
		 List<Comment> clist = s.getComments();
		 ArrayList<CommentBean> thread = new ArrayList<CommentBean>();
		 for(int i = 0; i < clist.size(); i++)
		 {
			 thread.add(new CommentBean(clist.get(i), curUser));
		 }
		 request.setAttribute("thread", thread);
		 
		 // Set if user can edit
		 if(curUser != null && (curUser.getUid() == s.getSubmitter()))
		 {
			 request.setAttribute("edittable", Boolean.toString(true));
		 }else
		 {
			 request.setAttribute("edittable", Boolean.toString(false));
		 }
		 
		 // Forward to success
		 return mapping.findForward("success");    		
	 }
}
