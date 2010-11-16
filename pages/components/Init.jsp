<%@ page import="com.parseexception.actionbeans.BaseAction" %>
<%@ page session="false" %>

<link rel="stylesheet" type="text/css" href="/css/root.css" />

<%
	// If this JSP wasn't loaded via an action, then run the boilerplate stuff
	if(request.getAttribute("loggedIn") == null)
	{
		BaseAction action = new BaseAction();
		action.verifyLogin(request, response, false);
	}
%>