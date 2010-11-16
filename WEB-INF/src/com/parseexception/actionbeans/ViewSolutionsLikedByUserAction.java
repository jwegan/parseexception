package com.parseexception.actionbeans;

import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.displaybeans.*;
import com.parseexception.model.*;

public class ViewSolutionsLikedByUserAction extends BaseAction {
	public ViewSolutionsLikedByUserAction()
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
		 String useridString =  request.getParameter("uid");
		 int uid;
		 int pageNum = 0;
		 try
		 {
			 uid = Integer.parseInt(useridString);
			 
			 if(request.getParameter("pageNum") != null)
				 pageNum = Integer.parseInt(request.getParameter("pageNum"));
		 }catch(Exception e)
		 {
			 ActionErrors errors = new ActionErrors();
			 errors.add("error", new ActionMessage("errors.badreq"));
			 saveErrors(request, errors);
			 return mapping.findForward("failure");
		 }
		 
		 // Add user bean to request
		 User u = User.getUser(uid);
		 request.setAttribute("user", new UserBean(u, curUser));
		 
		 // Add list of solutions liked by user
		 ArrayList<SolutionBean> likedSolutions = new ArrayList<SolutionBean>();
		 ReturnList<Solution> results = u.getLikedSolutions(pageNum, curUser, request.getParameter("tag"));
		 List<Solution> solutionList = results.list;
		 
		 for(int i = 0; i < solutionList.size(); i++)
		 {
			 int vote = 0;
			 Solution s = solutionList.get(i);
			 if(curUser != null)
				 vote = curUser.getVote(s.getSid());
			 likedSolutions.add(new SolutionBean(s, vote));
		 }
		 request.setAttribute("solutions", likedSolutions);
	 
	 	 // Set next prev
	 	 setNextPrev(request, pageNum, results); 
	 	String param = (request.getParameter("tag") == null)? "?uid=" + uid :
			   "?uid=" + uid + "&tag=" + request.getParameter("tag");
	 	 request.setAttribute("actionURL", "/likedbyuser" + param);
		 
		 // Forward to success
		 return mapping.findForward("success");    		
	 }		
}
