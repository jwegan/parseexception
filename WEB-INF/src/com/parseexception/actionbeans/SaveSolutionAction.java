package com.parseexception.actionbeans;


import javax.servlet.http.*;
import org.apache.struts.action.*;
public class SaveSolutionAction extends BaseAction {
	
	public SaveSolutionAction()
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
		 String saveResponse = "SUCCESS";
		 
		 // Verify login
		 if(!verifyLogin(request, response, true))
		 {
	 		 saveResponse = "LOGIN";
		 }else
		 {
			 try
			 {
				 int sid = Integer.parseInt(request.getParameter("sid"));
				 String action = request.getParameter("action");
				 
				 if(action != null && action.equals("remove") && !curUser.removeSavedSolution(sid))
				 {
					 saveResponse = "ERROR";
				 }else if(!curUser.setSavedSolution(sid))
				 {
					 saveResponse = "ERROR";
				 }
			 }catch(Exception e)
			 {
				 saveResponse = "ERROR";
			 }
			 
		 }
	 	
		 request.setAttribute("saveResponse", saveResponse);
		 return mapping.findForward("success");    		
	 }	
}

