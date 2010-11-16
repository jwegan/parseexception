<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Contact Us</title>

<%@ include file="../components/Init.jsp" %>
</head>

<!-- Body -->
<body class="pageBody">
<!-- Includes the page header -->
<%@ include file="../components/PageHeader.jsp" %>

<!-- Includes the side panel -->
<%@ include file="../components/SidePanel.jsp" %>

<!-- Content Pane -->
<div class="contentPane">
	<div class="formBox">
		<div class="formTitle">
			<strong>Contact Form</strong>
		</div>
		
		<logic:messagesPresent>
			<div class="errorPane">
				<strong>Errors:</strong><br /><br />
				<html:errors />
			</div>
		</logic:messagesPresent>
		
		<html:form action="/sendcontact">
		<table cellspacing="10" cellpadding="10">
			<tr>
				<td>
					Contact Type:
				</td>
				<td>
					<html:select property="contactType" styleClass="formField"
						value="<%= (request.getParameter("reportError") == null)? 
									"General Question" : "Report Problem" %>" >
						<html:option value="General Question" />
						<html:option value="Feedback" />
						<html:option value="Report Problem" />
					</html:select>
				</td>
			</tr>
			<tr>
				<td>
					Email Address:
				</td>
				<td>
					<html:text property="email" styleClass="formField" />
				</td>
			</tr>
			<tr>
				<td>
					Message:
				</td>
				<td>
					<html:textarea property="body" rows="6" cols="50" />
				</td>
			</tr>
		</table>
		<html:submit>Send Message</html:submit>
		</html:form>
	</div>
</div>

<!-- Includes footer -->
<%@ include file="../components/Footer.jsp" %>

</body>
</html:html>