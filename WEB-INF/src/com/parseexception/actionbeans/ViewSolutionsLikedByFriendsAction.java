package com.parseexception.actionbeans;

import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.Log;
import com.parseexception.displaybeans.SolutionBean;
import com.parseexception.model.*;

public class ViewSolutionsLikedByFriendsAction extends BaseAction {
	public ViewSolutionsLikedByFriendsAction()
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
		 
		 // Add list of solutions liked by user
		 ArrayList<SolutionBean> likedSolutions = new ArrayList<SolutionBean>();
		 ReturnList<Solution> results = 
			 DBQueries.getSolutionsLikedByFriends(curUser.getUid(), pageNum);
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
	 	 request.setAttribute("actionURL", "/likedbyfriends");
		 
		 // Forward to success
		 return mapping.findForward("success");    		
	 }	
}
