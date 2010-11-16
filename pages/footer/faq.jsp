<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="com.parseexception.Config" %>
<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Frequently Asked Questions</title>
<%@ include file="../components/Init.jsp" %>

<style type="text/css">
#faqPane {
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
<div class="contentPane" id="faqPane">
<strong>What is <%= Config.siteName %> about?</strong><br />
Rather than posting only when you have a programming question, ParseException allows
programmers to share their knowledge on how they overcame problems or issues with
the world.<br />

<!-- Includes footer -->
<%@ include file="../components/Footer.jsp" %>

</body>
</html:html>