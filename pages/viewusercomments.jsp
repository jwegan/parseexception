<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>View <bean:write name="user" property="username" />'s Comments</title>

<%@ include file="components/Init.jsp" %>
<link href="/css/prettify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/javascript/prettify.js"></script>
<style type="text/css">
#commentCell {
	background-color: #FFFFFF;
	border-bottom: 1px solid white;
}
</style>
</head>

<!-- Body -->
<body class="pageBody" onload="prettyPrint()">
<!-- Includes the page header -->
<%@ include file="components/PageHeader.jsp" %>

<!-- User Header -->
<%@ include file="components/UserHeader.jsp" %>

<!-- Includes the side panel -->
<%@ include file="components/SidePanel.jsp" %>

<!-- Content Pane -->
<div class="contentPane">
<%@ include file="components/DisplayCommentList.jsp" %>
</div>

<!-- Includes footer -->
<%@ include file="components/Footer.jsp" %>

</body>
</html:html>