package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.SpamFilter;
import com.parseexception.StringProcessor;
import com.parseexception.formbeans.SolutionForm;
import com.parseexception.model.*;

public class PostSolutionAction extends BaseAction {
	/*
	 * ctor
	 */
	public PostSolutionAction()
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
	 	
	 	// Validate form
    	SolutionForm solutionForm = (SolutionForm)form;
	 	ActionErrors errors = solutionForm.validate(mapping, request);
	 	if (errors.size() > 0)
	 	{
	     	saveErrors(request, errors);	  
	 		return mapping.findForward("failure");
	 	}
	 	
	 	// Process Form
	 	int sid = solutionForm.getSid();
	 	boolean bSuccess = false;
	 	Solution s = null;
	 	if(sid > 0)
	 	{
	 		s = Solution.getSolution(sid);
	 		if(curUser.getUid() == s.getSubmitter())
	 		{
		 		bSuccess = s.setQuestion(solutionForm.getQuestion());
		 		bSuccess &= s.setAnswer(StringProcessor.goingToDB(solutionForm.getAnswer()));
		 		bSuccess &= s.setAnswerRaw(solutionForm.getAnswer());
		 		bSuccess &= s.setIsPublic(solutionForm.getIsPublic().compareToIgnoreCase("public") == 0);
	 		}
	 	}else if(!SpamFilter.isSpam(curUser, solutionForm.getAnswer() + solutionForm.getQuestion(),
	 								request.getRemoteAddr())){
	 		s = new Solution(	curUser.getUid(), 
	 							solutionForm.getQuestion(), 
	 							StringProcessor.goingToDB(solutionForm.getAnswer()),
	 							solutionForm.getAnswer(),
	 							solutionForm.getIsPublic().compareToIgnoreCase("public") == 0);
	 		if(s.getSid() > 0)
	 		{
	 			bSuccess = true;
	 			sid = s.getSid();
	 		}
	 	}
	 	
     	// Add tags
     	String tags = solutionForm.getTags();
     	if(s!= null && tags != null && tags.length() > 0)
     	{ 
     		s.setTags(tags);
     	}
	 	
	 	// Forward
	 	if (bSuccess)
	 	{
	 		// Reset form
	     	solutionForm.reset(mapping, request);
	     	
	        // Forward to success
	     	request.setAttribute("sid", new Integer(sid));
	        return mapping.findForward("success");    		
	 	}
	 	else
	 	{
	 		// Add error
	 		errors.add("error", new ActionMessage("solution.failure"));
	 		saveErrors(request, errors);
	 		
	 		// Forward to failure
	 		return mapping.findForward("failure");
	 	}
	 }
}
