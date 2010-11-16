<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>About Us</title>
<%@ include file="../components/Init.jsp" %>
<style type="text/css">
#aboutPane {
	font-size: small;
}
</style>
</head>

<!-- Body -->
<body class="pageBody">
<!-- Includes the page header -->
<%@ include file="../components/PageHeader.jsp" %>

<!-- Includes the side panel -->
<%@ include file="../components/SidePanel.jsp" %>

<!-- Content Pane -->
<div class="contentPane" id="aboutPane">
<h3>About Us</h3><br />
The goal of this website is to allow programmers to share they knowledge and
solutions to programming or technical problems with the world so others can 
benefit from what you've learned. It also provides a tools to keep technical notes
so you can always access examples you've written down of how to execute certain
commands or how to use a certain API, etc.
</div>

<!-- Includes footer -->
<%@ include file="../components/Footer.jsp" %>

</body>
</html:html>