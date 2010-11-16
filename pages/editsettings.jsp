<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" />
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
<META HTTP-EQUIV="EXPIRES" CONTENT="0" />

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Edit Settings</title>
<%@ include file="components/Init.jsp" %>

<link href="/css/prettify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/javascript/prettify.js"></script>
<script type="text/javascript" src="/javascript/showdown.js"></script>
<script type="text/javascript" src="/javascript/preview.js"></script>
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
			<strong>Edit Account Settings</strong>
		</div>
		
		<logic:messagesPresent>
			<div class="errorPane">
				<strong>Errors:</strong><br /><br />
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
		
		<html:form action="/savesettings">
		<table cellspacing="10" cellpadding="10">		
			<!-- Change account information -->
			<tr>
				<td><div class="formTitle">
				Account Information:
				</div></td>
			</tr>
			<tr>
				<td>
					<strong>Display Name:</strong>
				</td>
				<td>
					<html:text property="username" styleClass="formField" />
				</td>
		    </tr>
			<tr>
				<td>
					Email:
				</td>
				<td>
					<html:text property="email" styleClass="formField" />
				</td>
			</tr>
			<tr>
				<td>
					Website:
				</td>
				<td>
					<html:text property="website" styleClass="formField" />
				</td>
			</tr>
			<tr>
				<td>
					About Yourself:
				</td>
				<td>
					<html:textarea property="about" cols="50" rows="15" styleId="inputPane" />
				</td>
			</tr>
			<tr>
				<td>
					Preview:
				</td>
				<td>
					<div id="previewPane" class="markdownPane">
					
					</div>
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<html:submit>Save Settings</html:submit>
				</td>
			</tr>
		</table>	
		</html:form>
	</div>
</div>

<!-- Includes footer -->
<%@ include file="components/Footer.jsp" %>

</body>
</html:html>