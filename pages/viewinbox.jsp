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
<title>Inbox</title>
<%@ include file="components/Init.jsp" %>

<style type="text/css">
#inboxCell {
	background-color: #FFFFFF;
	border-bottom: 1px solid white;
}

.selectAction {
	list-style-type: none;
	display: inline;
	font-size: small;
	position: relative;
	top: 2px;
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
<html:form action="/processmessages">
<div>
<table class="mboxTable">
	<!-- Table header -->
	<tr class="mboxHeader">
		<td class="mboxHeaderCell" style="min-width: 20px;">&nbsp;</td>
		<td class="mboxHeaderCell">From</td>
		<td class="mboxHeaderCell">Subject</td>
		<td class="mboxHeaderCell">Sent</td>
	</tr>
	
	<logic:iterate name="messages" id="message" indexId="messageIdx">
		<logic:equal name="message" property="unread" value="true">
		<tr class="unreadMessage">
		</logic:equal>
		<logic:equal name="message" property="unread" value="false">
		<tr class="readMessage">
		</logic:equal>
			<td class="mboxCell">
				<html:multibox property="selectedItems">
					<bean:write name="message" property="id" />
				</html:multibox>
			</td>
			<td class="mboxCell">
				<html:link action="viewuser" paramId="uid" paramName="message"
						   paramProperty="fromId">
					<bean:write name="message" property="fromUname" />
				</html:link>
			</td>
			<td class="mboxCell">
				<html:link action="viewmessage" paramId="id" paramName="message"
						   paramProperty="id">
					<bean:write name="message" property="subject" />
				</html:link>
			</td>
			<td class="mboxCell">
				<bean:write name="message" property="timestamp" />
			</td>
		</tr>
	</logic:iterate>
</table>
</div>

<div class="selectAction">
	<ul class="selectAction">
		<li class="selectAction">
			<html:select property="action">
				<html:option value="markasread">Mark As Read</html:option>
				<html:option value="markasunread">Mark As Unread</html:option>
				<html:option value="delete">Delete</html:option>
			</html:select>
		</li>
		<li class="selectAction">
			<html:submit>Submit</html:submit>
		</li>
	</ul>
</div>

</html:form>
</div>

<!-- Includes footer -->
<%@ include file="components/Footer.jsp" %>

</body>
</html:html>