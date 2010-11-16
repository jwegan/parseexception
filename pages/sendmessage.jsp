<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Compose Message</title>
<%@ include file="components/Init.jsp" %>

<style type="text/css">
#composeCell {
	background-color: #FFFFFF;
	border-bottom: 1px solid white;
}

.composeTable {
	margin-top: 10px;
}
</style>
</head>

<!-- Body -->
<body class="pageBody">
<!-- Includes the page header -->
<%@ include file="components/PageHeader.jsp" %>

<!-- Includes the messages header -->
<%@ include file="components/MessagesHeader.jsp" %>

<!-- Includes the side panel -->
<%@ include file="components/SidePanel.jsp" %>

<!-- Content Pane -->
<div class="contentPane">
	<div class="formBox">
		<div class="formTitle">
			Compose Message:
		</div>
	
		<logic:messagesPresent>
			<div class="errorPane">
				<strong>Errors:</strong><br /><br />
				<html:errors />
			</div>
		</logic:messagesPresent>
		
		<html:form action="sendmessage">
		<div class="composeTable">
		<table cellspace="10" cellpadding="10">
			<tr>
				<td>
					Username:
				</td>
				<td>
					<html:text property="toName" disabled="true" styleClass="formField" />
				</td>
			</tr>
			<tr>
				<td>
					Subject:
				</td>
				<td>
					<html:text property="subject" styleClass="formField" />
				</td>
			</tr>			
			<tr>
				<td>
					Message:
				</td>
				<td>
					<html:textarea property="body" cols="50" rows="5" />
				</td>
			</tr>
			<tr>
				<td>
					<html:submit>Send Message</html:submit>
				</td>
			</tr>
		</table>
		</div>
		</html:form>
	</div>
</div>

<!-- Includes footer -->
<%@ include file="components/Footer.jsp" %>

</body>
</html:html>