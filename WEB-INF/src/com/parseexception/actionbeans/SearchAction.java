package com.parseexception.actionbeans;

import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.displaybeans.*;
import com.parseexception.formbeans.SearchForm;
import com.parseexception.model.*;

public class SearchAction extends BaseAction {
	public SearchAction()
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
		 verifyLogin(request, response, false);
		 
		 // Process search parameters
		 SearchForm sform = (SearchForm) form;
		 int searchType = sform.getSearchType();
		 String ordering = sform.getOrder();
		 String searchstr = sform.getSearchStr();
		 
		 int uid = (curUser == null)? 0 : curUser.getUid(); 
		 int pageNum = 0;
		 try
		 {
			 pageNum = Integer.parseInt(request.getParameter("pageNum"));
		 }catch(Exception e)
		 {
			 pageNum = 0;
		 }
		 
		 // Get results
		 ReturnList<Solution> results;
		 if(searchType == 0)
		 {
			 results = DBQueries.solutionSearch(searchstr, ordering, uid, pageNum);
		 }else if(searchType == 1)
		 {
			 results = DBQueries.tagSearch(searchstr, ordering, uid, pageNum);
		 }else
		 {	 // Handle user search
			 ReturnList<User> users = DBQueries.userSearch(searchstr, pageNum);
			 List<User> userList = users.list;
			 ArrayList<UserBean> ubeans = new ArrayList<UserBean>();
			 for(int i = 0; i < userList.size(); i++)
			 {
				 User u = userList.get(i);
				 ubeans.add(new UserBean(u, curUser));
			 }
			 
			 // Set next prev
			 StringBuilder querystr = new StringBuilder("/search?");
			 querystr.append("searchStr=");
			 querystr.append(searchstr);
			 querystr.append("&order=");
			 querystr.append(ordering);
			 querystr.append("&searchType=");
			 querystr.append(searchType);
			 
			 setNextPrev(request, pageNum, users);
			 request.setAttribute("actionURL", querystr.toString());
			 
			 request.setAttribute("users", ubeans);
			 return mapping.findForward("success");    
		 }
		 
		 // Put solutions into beans
		 List<Solution> solutionList = results.list;
		 ArrayList<SolutionBean> sbeans = new ArrayList<SolutionBean>();
		 for(int i = 0; i < solutionList.size(); i++)
		 {
			 Solution s = solutionList.get(i);
			 int vote = (curUser == null)? 0 : curUser.getVote(s.getSid());
			 sbeans.add(new SolutionBean(s, vote));
		 }
		   	
		 // Set next prev
		 StringBuilder querystr = new StringBuilder("/search?");
		 querystr.append("searchStr=");
		 querystr.append(searchstr);
		 querystr.append("&order=");
		 querystr.append(ordering);
		 querystr.append("&searchType=");
		 querystr.append(searchType);
		 setNextPrev(request, pageNum, results);
		 request.setAttribute("actionURL", querystr.toString());
		 	
		 // Forward to success
		 request.setAttribute("solutions", sbeans);
		 return mapping.findForward("success");    
	 }

}
