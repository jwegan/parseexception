<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Search</title>

<%@ include file="components/Init.jsp" %>
<style type="text/css">
#searchString
{
	width: 500px;
}

#searchPane
{
	font-size: x-small;
}
</style>

</head>

<!-- Body -->
<body class="pageBody">
<!-- Includes the page header -->
<%@ include file="components/PageHeader.jsp" %>

<!-- Includes the side panel -->
<%@ include file="components/SidePanel.jsp" %>

<!-- Content Pane -->
<div class="contentPane" id="searchPane">
	<html:form action="search">
		<html:text property="searchStr" styleId="searchString" /><br />
		Search Type:
		<html:select property="searchType">
			<html:option value="0">Normal Search</html:option>
			<html:option value="1">Tag Search</html:option>
			<html:option value="2">User Search</html:option>
		</html:select>
		Sort Order:
		<html:select property="order">
			<html:option value="highestScore">Score</html:option>
			<html:option value="dateNewest">Newest</html:option>
			<html:option value="dateOldest">Oldest</html:option>
		</html:select>
		<html:submit value="Search" />
	</html:form>

	<logic:present name="solutions">
		<%@ include file="components/DisplaySolutionList.jsp" %>
	</logic:present>
	<logic:present name="users">
		<%@ include file="components/DisplayUserList.jsp" %>
	</logic:present>
</div>

<!-- Includes footer -->
<%@ include file="components/Footer.jsp" %>

</body>
</html:html>