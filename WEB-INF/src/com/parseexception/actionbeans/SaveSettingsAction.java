package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.StringProcessor;
import com.parseexception.formbeans.SettingsForm;
import com.parseexception.model.*;

public class SaveSettingsAction extends BaseAction {
	public SaveSettingsAction()
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
	 	
	 	
	   	SettingsForm settingForm = (SettingsForm) form;
	   	Settings s = Settings.getSettings(curUser.getUid());
	   	
	   	// Validate form
	   	ActionErrors errors = settingForm.validate(mapping, request);
	   	ActionMessages messages = new ActionMessages();
	 	if (errors.size() > 0)
	 	{
	     	saveErrors(request, errors);
	 		return mapping.findForward("failure");
	 	}

	   	
	   	// Save settings to database
	 	s.setAbout(StringProcessor.goingToDB(settingForm.getAbout()));
	 	s.setAboutRaw(settingForm.getAbout());
	 	s.setEmail(settingForm.getEmail());
	 	s.setWebsite(settingForm.getWebsite());
	 	curUser.setUsername(settingForm.getUsername());
	 	
	   	messages.add("message", new ActionMessage("settings.success"));
	   	saveMessages(request, messages);
	 	
	    // Forward to success
	    return mapping.findForward("success");    		
	 }	
}
