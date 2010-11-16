package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.formbeans.SettingsForm;
import com.parseexception.model.*;

public class ViewSettingsAction extends BaseAction {
	public ViewSettingsAction()
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
		 
	 	// Populate settings form
	   	SettingsForm settingForm = (SettingsForm) form;
	   	Settings s = Settings.getSettings(curUser.getUid());
	   	
	   	settingForm.setAbout(s.getAboutRaw());
	   	settingForm.setEmail(s.getEmail());
	   	settingForm.setWebsite(s.getWebsite());
	   	settingForm.setUsername(curUser.getUsername());
	 	
	    // Forward to success
	    return mapping.findForward("success");    		
	 }
}
