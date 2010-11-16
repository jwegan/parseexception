<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Login</title>
<%@ include file="components/Init.jsp" %>

<!-- Simple OpenID Selector -->
	<link rel="stylesheet" href="/css/openid.css" />
	<script type="text/javascript" src="/javascript/jquery-1.2.6.min.js"></script>
	<script type="text/javascript" src="/javascript/openid-jquery.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		    openid.init('provider');
		});
	</script>
<!-- /Simple OpenID Selector -->
</head>

<!-- Body -->
<body class="pageBody">
<!-- Includes the page header -->
<%@ include file="components/PageHeader.jsp" %>

<!-- Includes the side panel -->
<%@ include file="components/SidePanel.jsp" %>

<!-- Content Pane -->
<div class="contentPane">
	<div class="formBox">
		<div class="formTitle">
			<strong>ParseException Login</strong>
		</div>
		
		<logic:messagesPresent>
			<div class="errorPane">
				<strong>Errors:</strong>
				<html:errors />
			</div>
		</logic:messagesPresent>
		
		<logic:messagesPresent message="true">
			<div class="notificationPane">
				<strong>Notifications:</strong><br /><br />
				<ul>
				<html:messages message="true" id="message">
					<li>
						<bean:write name="message"/>
					</li>
				</html:messages>
				</ul>
			</div>
		</logic:messagesPresent>
		
		<div id="openid_choice">
			<br />
	    	<p>Please click your account provider:</p>
	    	<div id="openid_btns"></div>
		</div>
			
		<html:form action="/login">	
			<div id="openid_input_area">
					<html:text property="provider" styleClass="formField" styleId="openid_identifier" value="http://"/>
					<html:submit>Login</html:submit>
			</div>
		</html:form>
		
		<div style="text-size: small;">
			<p>OpenID is service that allows you to log-on to many different websites using a single indentity.
			Find out <a href="http://openid.net/what/">more about OpenID</a> and <a href="http://openid.net/get/">if you may already have an OpenID enabled account</a>.</p>
			If you don't have an OpenID you can create one at <a href="http://myopenid.com">MyOpenId.com</a>
		</div>
		
	</div>
</div>

<!-- Includes footer -->
<%@ include file="components/Footer.jsp" %>

</body>
</html:html>