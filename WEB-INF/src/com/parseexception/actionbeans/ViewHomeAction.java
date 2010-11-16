package com.parseexception.actionbeans;


import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.Log;
import com.parseexception.displaybeans.*;
import com.parseexception.model.*;

public class ViewHomeAction extends BaseAction {
	public ViewHomeAction()
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
	   	
	   	// Get parameters
	   	int pageNum = 0;
	   	String tab = "hot";
	   	try
	   	{
	   		if(request.getParameter("pageNum") != null)
	   			pageNum = Integer.parseInt(request.getParameter("pageNum"));
	   		
	   		if(request.getParameter("tab") != null)
	   			tab = request.getParameter("tab");
	   	}catch(Exception e)
	   	{
	   		Log.log(e, Log.FATAL_ERROR);
	   	}

	   	// Get solutions
	   	List<Solution> solutionList;
	   	int uid = (curUser != null)? curUser.getUid() : 0;
	   	ReturnList<Solution> results;
	 	if(tab.compareTo("best") == 0)
	 	{
	 		results = DBQueries.getBestSolutions(uid, pageNum);
	 	}else if(tab.compareTo("new") == 0){
	 		results = DBQueries.getNewestSolutions(uid, pageNum);
	 	}else{
	 		results = DBQueries.getHotSolutions(uid, pageNum);
	 	}
	 	
	 	// Put solutions into beans
	 	solutionList = results.list;
	 	ArrayList<SolutionBean> sbeans = new ArrayList<SolutionBean>();
	 	for(int i = 0; i < solutionList.size(); i++)
	 	{
	 		Solution s = solutionList.get(i);
	 		int vote = (curUser == null)? 0 : curUser.getVote(s.getSid());
	 		sbeans.add(new SolutionBean(s, vote));
	 	}
	   	
	 	// Set next prev
	 	setNextPrev(request, pageNum, results);
	 	request.setAttribute("actionURL", "/viewhome?tab=" + tab);
	 	
	    // Forward to success
	 	request.setAttribute("solutions", sbeans);
	 	request.setAttribute("tab", tab);
	    return mapping.findForward("success");    		
	 }	
}
