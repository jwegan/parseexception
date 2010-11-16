<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><bean:write name="user" property="username" /></title>

<%@ include file="components/Init.jsp" %>

<link href="/css/prettify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/javascript/prettify.js"></script>
<style type="text/css">
.joindate {
	font-size: x-small;
}

.smallText {
	font-size: small;
}

#aboutCell {
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
	<!-- User details -->
	<h3><bean:write name="user" property="username" /></h3>
	<div class="joindate">
		Joined <bean:write name="user" property="joinDate" />
	</div>
	<div class="actionLinks">
		<ul class="actionLinks">
			<li class="actionLinks">
				<html:link action="/compose" paramId="uid" paramName="user" paramProperty="uid">
					Send message
				</html:link>
			</li>
			<logic:notEqual name="user" property="isLoggedInUser" value="true">
				<li class="actionLinks">
					<logic:equal name="user" property="isFriend" value="true">
						<html:link action="/removefriend"
						 paramId="uid" paramName="user" paramProperty="uid">
							Remove Friend
						</html:link>
					</logic:equal>
					<logic:equal name="user" property="isFriend" value="false">
						<html:link action="/addfriend"
						 paramId="uid" paramName="user" paramProperty="uid">
							Follow
						</html:link>
					</logic:equal>
				</li>
			</logic:notEqual>
		</ul>
	</div>
	
	<br /><br />
	<div class="smallText">
		Karma: <bean:write name="user" property="karma" /><br />
		Website: <bean:write name="user" property="website" /><br />
		About:<br />
	</div>
	
	<div class="smallText markdownPane" style="margin-top: 5px;">
		<bean:write name="user" property="about" filter="false" />
	</div>
</div>

<!-- Includes footer -->
<%@ include file="components/Footer.jsp" %>

</body>
</html:html>