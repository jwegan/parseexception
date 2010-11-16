<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Post Solution</title>
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
			<strong>Post New Solution</strong>
		</div>
		
		<logic:messagesPresent>
			<div class="errorPane">
				<strong>Errors:</strong><br /><br />
				<html:errors />
			</div>
		</logic:messagesPresent>
		
		<html:form action="/postsolution">
		<html:hidden property="sid" />
		<table cellspacing="10" cellpadding="10">
			<tr>
				<td>
					<strong>Question:</strong>
				</td>
				<td>
					<html:text property="question" styleClass="formField" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>Tags:</strong><br />
					<span style="font-size: x-small;">
						ex: tomcat, classpath
					</span>
				</td>
				<td>
					<html:text property="tags" styleClass="formField" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>Visibilty:</strong>
				</td>
				<td>
					<html:select property="isPublic">
						<html:option value="public">Public</html:option>
						<html:option value="private">Private</html:option>
					</html:select>
				</td>
			</tr>
			<tr>
				<td>
					<strong>Answer:</strong>
				</td>
				<td>
					<html:textarea property="answer" cols="50" rows="15" styleId="inputPane" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>Preview:</strong>
				</td>
				<td>
					<div id="previewPane" class="markdownPane">
					
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<html:submit>Post Solution</html:submit>
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