package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.model.Solution;
import com.parseexception.model.User;

public class SubmitVoteAction extends BaseAction {
	public SubmitVoteAction()
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
		 String voteResponse = "SUCCESS";
		 
		 // Verify login
		 if(!verifyLogin(request, response, true))
		 {
	 		 voteResponse = "LOGIN";
		 }else
		 {
			 try
			 {
				 int sid = Integer.parseInt(request.getParameter("sid"));
				 int vote = Integer.parseInt(request.getParameter("vote"));
				 if(!curUser.setVote(sid, vote))
				 {
					 voteResponse = "ERROR";
				 }
				 
				 Solution s = Solution.getSolution(sid);
				 User u = User.getUser(s.getSubmitter());
				 if(vote == 1)
					 u.incrementKarma();
				 else if(vote == 2)
					 u.decrementKarma();
			 }catch(Exception e)
			 {
				 voteResponse = "ERROR";
			 }
			 
		 }
	 	
		 request.setAttribute("voteResponse", voteResponse);
		 return mapping.findForward("success");    		
	 }	
}
