<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><bean:write name="selectedmsg" property="subject" /></title>
<%@ include file="components/Init.jsp" %>

<style type="text/css">
#viewmsgCell {
	background-color: #FFFFFF;
	border-bottom: 1px solid white;
}

#msgThread {
	margin-top: 15px;
}

.msgSubject {
	font-weight: bold;
	margin-bottom: 10px;
}

.msgDetails {
	font-size: x-small;
}

.msgBody {
	border: 1px solid black;
	background-color: #EEEEEE;
	font-size: small;
	margin-top: 1px;
	margin-bottom: 10px;
	padding: 3px;
	-moz-border-radius: 5px;
}

.actionLinks {
	font-size: x-small;
	display: inline;
	list-style-type: none;
	margin-left: 15px;
}

.actionDiv {
	border-bottom: 1px solid black;
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

<logic:notPresent name="messages">
No message selected.
</logic:notPresent>

<logic:present name="messages">
	<!-- Subject -->
	<div class="msgSubject">
		<bean:write name="selectedmsg" property="subject" />
	</div>
	
	<!-- Reply box -->
	<div class="formBox">
		<html:form action="sendmessage">
			<html:hidden property="threadid" />
			<html:hidden property="subject" />
			<html:hidden property="toName" />
			<html:textarea property="body" rows="6" cols="50" /><br /><br />
			<html:submit>Reply</html:submit>
		</html:form>
	</div>
	
	<!-- Actions -->
	<div class="actionDiv">
		<ul>
			<li class="actionLinks">
				<html:link action="processmessages?action=markasunread"
					paramId="id" paramName="selectedmsg" paramProperty="id">
					Mark as Unread
				</html:link>
			</li>
			<li class="actionLinks">
				<html:link action="processmessages?action=delete"
					paramId="id" paramName="selectedmsg" paramProperty="id">
					Delete
				</html:link>
			</li>	
		</ul>
	</div>
	
	<!-- Thread -->
	<div id="msgThread">
	<logic:iterate name="messages" id="message" indexId="messageIdx">
		<div class="msgDetails">
			Sent by <html:link action="viewuser" paramId="uid"
								paramName="message" paramProperty="fromId">
			<bean:write name="message" property="fromUname" />
			</html:link> on <bean:write name="message" property="timestamp" />
		</div>
		<div class="msgBody">
			<bean:write name="message" property="body" />
		</div>
	</logic:iterate>
	</div>
</logic:present>
</div>

<!-- Includes footer -->
<%@ include file="components/Footer.jsp" %>

</body>
</html:html>