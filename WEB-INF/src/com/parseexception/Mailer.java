package com.parseexception;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.MimeMessage;


public class Mailer 
{
	public static boolean sendEmail(String to, String from, String subject,
									String body)
	{
		 Properties props = new Properties();
		 props.put("mail.smtp.host", Config.smtpServer);
		 props.put("mail.from", from);
		 Session session = Session.getInstance(props, null);

		 try {
			 MimeMessage msg = new MimeMessage(session);
		     msg.setFrom();
		     msg.setRecipients(Message.RecipientType.TO, to);
		     msg.setSubject(subject);
		     msg.setText(body);
		     msg.setSentDate(new Date());
		     Transport.send(msg);
		 } catch (Exception e) {
			 Log.log(e, Log.FATAL_ERROR);
			 return false;
		 }

		 return true;
	}
}
