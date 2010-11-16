package com.parseexception.actionbeans;

import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.displaybeans.*;
import com.parseexception.model.*;
public class ViewSavedSolutionsAction extends BaseAction 
{
	public ViewSavedSolutionsAction()
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
		 int uid = curUser.getUid();
		 int pageNum = 0;
		 try
		 { 
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
		 
		 // Add list of solutions saved by user
		 ArrayList<SolutionBean> savedSolutions = new ArrayList<SolutionBean>();
		 ReturnList<Solution> results = u.getSavedSolutions(pageNum, request.getParameter("tag"));
		 List<Solution> solutionList = results.list;
		 for(int i = 0; i < solutionList.size(); i++)
		 {
			 int vote = 0;
			 Solution s = solutionList.get(i);
			 if(curUser != null)
				 vote = curUser.getVote(s.getSid());
			 savedSolutions.add(new SolutionBean(s, vote));
		 }
		 request.setAttribute("solutions", savedSolutions);
		 
	 	 // Set next prev
	 	 setNextPrev(request, pageNum, results);
	 	 String tagparam = (request.getParameter("tag") == null)? "" :
	 		 			   "?tag=" + request.getParameter("tag");
	 	 request.setAttribute("actionURL", "/savedsolutions" + tagparam);

		 // Forward to success
		 return mapping.findForward("success");    		
	 }		
}
