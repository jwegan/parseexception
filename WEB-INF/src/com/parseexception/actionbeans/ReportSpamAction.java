package com.parseexception.actionbeans;

import javax.servlet.http.*;
import org.apache.struts.action.*;
import java.util.*;

import com.parseexception.*;

public class ReportSpamAction extends BaseAction {
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
		 // Verify login, only logged in users can report spam
		 if(!verifyLogin(request, response, true))
		 {
	 		 return mapping.findForward("failure");
		 }
		 
		 String table = request.getParameter("type");
		 String id = request.getParameter("id");
		 String to = Config.smtpSpamEmail;
		 String from = Config.smtpSpamEmail;
		 
		 // Construct title
		 StringBuilder title = new StringBuilder();
		 title.append("Spam report from ");
		 title.append(curUser.getUsername());
		 
		 // Construct message
		 StringBuilder body = new StringBuilder();
		 body.append(curUser.getUsername());
		 body.append(" report the following ");
		 body.append(table);
		 body.append("as a potential violation.\n");
		 body.append("Report submitted on ");
		 body.append((new Date()).toString());
		 body.append("\nhttp://www.");
		 body.append(Config.siteName);
		 
		 if(table.equals("solution"))
		 {
			 body.append("/viewsolution.do?sid=");
		 }else
		 {
			 body.append("/viewcomment.do?id=");
		 }
		 
		 body.append(id);
		 body.append("\n");
		 
		 // Send Message
		 if(!Mailer.sendEmail(to, from, title.toString(), body.toString()))
			 return mapping.findForward("failure");
		 else
			 return mapping.findForward("success");
		 
		 
	 }
	
}
