package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.formbeans.SolutionForm;
import com.parseexception.model.Solution;

public class EditSolutionAction extends BaseAction {
	/*
	 * ctor
	 */
	public EditSolutionAction()
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
	 	int sid;
	 	if(sidString != null )
	 	{	
	 		try
	 		{
	 			sid = Integer.parseInt(sidString);
	 		}catch(Exception e)
	 		{
	 			ActionErrors errors = new ActionErrors();
	 			errors.add("error", new ActionMessage("errors.badreq"));
	 			saveErrors(request, errors);
	 			return mapping.findForward("failure");
	 		}
	 		
	 		// Populate Form if editting existing solution
	    	SolutionForm solutionForm = (SolutionForm)form;
		 	Solution solution = Solution.getSolution(sid);
		 	solutionForm.setSid(sid);
		 	solutionForm.setAnswer(solution.getAnswerRaw());
		 	solutionForm.setQuestion(solution.getQuestion());
		 	solutionForm.setTags(solution.getTagsAsString());
		 	
		 	// Verify person editting is the submitter
		 	if(curUser.getUid() != solution.getSubmitter())
		 	{
	 			ActionErrors errors = new ActionErrors();
	 			errors.add("error", new ActionMessage("errors.badreq"));
	 			saveErrors(request, errors);
	 			return mapping.findForward("failure");
		 	}
	 	}

        // Forward to success
        return mapping.findForward("success");    		
	 }
}
