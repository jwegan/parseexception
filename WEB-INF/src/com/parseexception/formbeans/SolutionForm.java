package com.parseexception.formbeans;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class SolutionForm extends ActionForm {
	private static final long serialVersionUID = 4530328638068545883L;
	
	private int sid = 0;
	private String question = "";
	private String answer = "";
	private String tags = "";
	private String isPublic = "true";
	
	/* 
	 * ctor
	 */
	public SolutionForm()
	{
		super();
	}
	
    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionMessages</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionMessages</code> object with no
     * recorded error messages.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     *
     * @return ActionMessages if any validation errors occurred
     */
    public ActionErrors validate (
        ActionMapping mapping,
        HttpServletRequest request) 
    {
        ActionErrors errors = new ActionErrors();
        
        // Check question
        if (question == null || question.length() < 3)
        {
        	errors.add("error", new ActionMessage("errors.invalid", "Question"));
        }
        
        // Check answer
        if(answer == null || answer.length() < 3)
        {
        	errors.add("error", new ActionMessage("errors.invalid", "Answer"));
        }
        
        return errors;
    }

	/************************************************************************************
	 * Getter & Setter methods
	 ************************************************************************************/
    /* sid */
    public int getSid()
    {
    	return sid;
    }
    
    public void setSid(int i)
    {
    	sid = i;
    }
    
    /* question */
    public String getQuestion()
    {
    	return question;
    }
    
    public void setQuestion(String s)
    {
    	question = s.trim();
    }
    
    /* answer */
    public String getAnswer()
    {
    	return answer;
    }
    
    public void setAnswer(String s)
    {
    	answer = s.trim();
    }
    
    /* tags */
    public String getTags()
    {
    	return tags;
    }
    
    public void setTags(String s)
    {
    	tags = s.trim().toLowerCase();
    }
    
    /* isPublic */
    public String getIsPublic()
    {
    	return isPublic;
    }
    
    public void setIsPublic(String s)
    {
    	isPublic = s.trim();
    }
}
