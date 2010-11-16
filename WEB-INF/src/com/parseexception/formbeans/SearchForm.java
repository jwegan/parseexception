package com.parseexception.formbeans;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.*;

public class SearchForm extends ActionForm {
	private static final long serialVersionUID = 8733634715091556466L;
	
	private String searchStr = "";
	private int searchType = 0;
	private String order = "";
	
	/*
	 * Search Types:
	 * 		0 - Search solutions
	 * 		1 - Search for solutions with tag
	 * 		2 - Search users
	 */
	
	/*
	 * ctor
	 */
	public SearchForm()
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
        
        // Check searchStr
        if (searchStr == null || searchStr.length() < 3)
        {
        	errors.add("error", new ActionMessage("search.invalid", "Search string"));
        }
        
        // Check searchType
        if (searchType < 0 || searchType > 2)
        {
        	errors.add("error", new ActionMessage("search.invalid", "Search type"));
        }
        
        return errors;
    }

	/************************************************************************************
	 * Getter & Setter methods
	 ************************************************************************************/
    /* searchStr */
    public String getSearchStr()
    {
    	return searchStr;
    }
    
    public void setSearchStr(String s)
    {
    	searchStr = s.trim();
    }
    
    /* searchType */
    public int getSearchType()
    {
    	return searchType;
    }
    
    public void setSearchType(int i)
    {
    	searchType = i;
    }
    
    /* order */
    public String getOrder()
    {
    	return order;
    }
    
    public void setOrder(String s)
    {
    	order = s.trim();
    }
}
