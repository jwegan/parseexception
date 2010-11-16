package com.parseexception;

import java.security.MessageDigest;
import java.util.*;
import org.pegdown.PegDownProcessor;

public class StringProcessor {
	private static PegDownProcessor parser = new PegDownProcessor();
	
	/************************************************************************************
	 * Process strings go/coming from the DB
	 ************************************************************************************/
	public static String goingToDB(String s)
	{
		// Add two spaces to end of lines so markdown puts in <br />
		//s = s.replaceAll("\r", "");
		//s = s.replaceAll("\n", "  \n"); 
		
		// Change http://<link> to a hyperlink
		s = s.replaceAll("(\\s)(http://[\\S]+)(\\s)", "$1[$2]($2)$3");
		
		// Prevent inword emphasis		
		s = s.replaceAll("([a-zA-Z0-9])_([a-zA-Z0-9])", "$1\\\\_$2");
		s = s.replaceAll("([a-zA-Z0-9])__([a-zA-Z0-9])", "$1\\\\_\\\\_$2");
		s = s.replaceAll("([a-zA-Z0-9])\\*([a-zA-Z0-9])", "$1\\\\*$2");
		s = s.replaceAll("([a-zA-Z0-9])\\*\\*([a-zA-Z0-9])", "$1\\\\*\\\\*$2");
		
		
		synchronized(parser)
		{
			s = parser.markdownToHtml(s);
		}
		
		s = s.replaceAll("<code>", "<code class=\"prettyprint\">");
		s = s.replaceAll("\n", "<br/>");
		
		if(s.endsWith("<br/>"))
		{
			s = s.substring(0, s.length() - 5);
		}
		
		// Add in header conversion
		//s = s.replaceAll("<br/>\\s*<br/>((\\*|</?em>|_)\\s*){3,}?<br/>", "<hr>");
		//s = s.replaceAll("<br/>\\s*<br/>", "<hr>");
		//System.err.println("\n<br/>abc<br/>".matches("(?s).*<br/>.*<br/>.*"));
		//s = s.replaceAll("(?s)(<br/>|<p>)(.*?\\S.*?)<br/>\\s*=[\\s=]*<br/>", "$1<h1>$2</h1><br />");
		//s = s.replaceAll("(?s)(<br/>|<p>)(.*?\\S.*?)<br/>\\s*-[\\s-]*<br/>", "$1<h2>$2</h2><br />");
		
		return s;
	}
	
	public static String goingToDisplay(String s)
	{
		return s;
	}
	
	public static String goingToEdit(String s)
	{
		return s;
	}
	
	/************************************************************************************
	 * Validate an email address
	 ************************************************************************************/
	public static boolean verifyEmailAddress(String s)
	{
		return s.matches("^[\\w.%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
	}
	
	/************************************************************************************
	 * Check a string to see if it has any non-numeric, non-digit, non-underscore chars
	 ************************************************************************************/
	public static boolean hasInvalidCharacters(String s)
	{
		return s.matches(".*\\W.*");
	}
	
	/************************************************************************************
	 * Generate random password
	 ************************************************************************************/
	public static String generatePassword()
	{
		Random rng = new Random();
		StringBuilder pword = new StringBuilder(11);
		
		for(int i = 0; i < 10; i++)
		{
			int rand = rng.nextInt(62);
			char c;
			
			if(rand < 26)
				c = (char) ('a' +  rand);
			else if(rand < 52)
				c = (char) ('A' + rand - 26);
			else
				c = (char) ('0' + rand - 52);
			
			pword.append(c);
		}
		
		return pword.toString();
	}
	
	/************************************************************************************
	 * Caculate a SHA1 hash and return it as a string.
	 ************************************************************************************/
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }
 
    public static String SHA1(String text) 
    {
    	try{
		    MessageDigest md;
		    md = MessageDigest.getInstance("SHA-1");
		    byte[] sha1hash = new byte[40];
		    md.update(text.getBytes("iso-8859-1"), 0, text.length());
		    sha1hash = md.digest();
		    return convertToHex(sha1hash);
    	}catch(Exception e){
    		Log.log(e, Log.FATAL_ERROR);
    	}
    	
    	return "";
    }
    
	/************************************************************************************
	 * Test Driver
	 ************************************************************************************/
	public static void main(String args[])
	{
		String markdown = "test";

		String html = StringProcessor.goingToDB(markdown);
		
		/*
		try
		{
			BufferedReader in = new BufferedReader(new FileReader("C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\webapps\\RemoteSystemsTempFiles\\test.txt"));
			String line;
			markdown = "";
			while((line = in.readLine()) != null)
			{
				markdown += line + "\n";
			}
			in.close();
		}catch(Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		//System.err.println(markdown);
		Date start = new Date();
		html = StringProcessor.goingToDisplay(markdown);
		Date end = new Date();
		
		System.out.println("runtime: " + (end.getTime() - start.getTime()));
		
		//System.out.println(markdown + "\n"); */
		System.out.println(html);
		
	}
}
