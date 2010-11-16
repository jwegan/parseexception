package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;

import com.parseexception.*;
import com.parseexception.formbeans.*;

public class SendContactAction extends BaseAction {
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
		 ContactForm cform = (ContactForm) form;
		 verifyLogin(request, response, false);

		 // Validate form
		 ActionErrors errors = cform.validate(mapping, request);
		 if (errors.size() > 0)
		 {
		     saveErrors(request, errors);
		 	 return mapping.findForward("failure");
		 }
		 String from = cform.getEmail();
		 String to = (cform.getContactType().equals("Report Error"))?
				 	 Config.smtpErrorsEmail : Config.smtpCommentsEmail;
		 
		 // Subject
		 StringBuilder subject = new StringBuilder();
		 subject.append("[");
		 subject.append(cform.getContactType());
		 subject.append("] ");
		 if(curUser != null)
			 subject.append(curUser.getUsername());
		 
		 // Set Body
		 StringBuilder body = new StringBuilder();
		 body.append("Request Type: ");
		 body.append(cform.getContactType());
		 body.append("\nReturn Email: ");
		 body.append(cform.getEmail());
		 if(curUser != null)
		 {
			 body.append("\nUser Name:");
			 body.append(curUser.getUsername());
		 }
		 body.append("\nMessage:\n");
		 body.append(cform.getBody());

		 // Send email
		 if(!Mailer.sendEmail(to, from, subject.toString(), body.toString()))
		 {
			 errors.add("error", new ActionMessage("contact.error"));
		     return mapping.findForward("failure");
		 }

		 return mapping.findForward("success");
	 }
}
