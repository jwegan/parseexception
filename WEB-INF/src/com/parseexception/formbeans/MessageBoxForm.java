package com.parseexception.formbeans;

import org.apache.struts.action.ActionForm;

public class MessageBoxForm extends ActionForm {
	private static final long serialVersionUID = 7142579634423960029L;
	
	/* fields */
	private String[] selectedItems = {};
	private String action = "";
	
	public MessageBoxForm()
	{
		super();
	}
	
	/* selectedItems */
	public String[] getSelectedItems()
	{
		return selectedItems;
	}
	
	public void setSelectedItems(String[] strAry)
	{
		selectedItems = strAry;
	}
	
	/* action */
	public String getAction()
	{
		return action;
	}
	
	public void setAction(String s)
	{
		action = s;
	}
}
