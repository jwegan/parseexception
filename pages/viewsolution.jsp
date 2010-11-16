<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
    
    
<html:html xhtml="true" lang="true">
<!-- Header -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><bean:write name="solution" property="question" /></title>
<%@ include file="components/Init.jsp" %>
<link href="/css/prettify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="/javascript/prettify.js"></script>
<script type="text/javascript" src="/javascript/vote.js"></script>

<style type="text/css">
.tagList
{
	font-size: x-small;
	margin: 5px;
}
</style>
</head>

<!-- Body -->
<body class="pageBody" onload="prettyPrint()">
<!-- Includes the page header -->
<%@ include file="components/PageHeader.jsp" %>

<!-- Includes the side panel -->
<%@ include file="components/SidePanel.jsp" %>

<!-- Content Pane -->
<div class="contentPane">
	<!-- Display solution -->
	<div class="solutionTitle"><bean:write name="solution" property="question" /></div>
	
	<table>
		<tr>
			<td style="text-align: center">
				<logic:equal name="edittable" value="false">
					<logic:equal name="solution" property="userVote" value="1">
						<img src="/images/uparrow_green.png" id="uparrow"
						     onclick="submitVote(<bean:write name="solution" property="sid" />, this)" />
					</logic:equal>
					<logic:notEqual name="solution" property="userVote" value="1">
						<img src="/images/uparrow_black.png" id="uparrow"
						     onclick="submitVote(<bean:write name="solution" property="sid" />, this)" />
					</logic:notEqual>
				</logic:equal>
			
				<div id="score" class="solutionScore" style="padding-right: 0px">
				<bean:write name="solution" property="score" />
				</div>
				
				<logic:equal name="edittable" value="false">
					<logic:equal name="solution" property="userVote" value="2">
						<img src="/images/downarrow_red.png" id="downarrow"
						     onclick="submitVote(<bean:write name="solution" property="sid" />, this)" />
					</logic:equal>
					<logic:notEqual name="solution" property="userVote" value="2">
						<img src="/images/downarrow_black.png" id="downarrow"
						     onclick="submitVote(<bean:write name="solution" property="sid" />, this)" />
					</logic:notEqual>
				</logic:equal>
			</td>
			<td>
				<div class="solutionSubmission">
					Originally posted by 
					<html:link action="/viewuser" paramId="uid" paramName="solution"
							   paramProperty="uid">
							 <bean:write name="solution" property="uname" />
					</html:link>
					<bean:write name="solution" property="timestamp" />
				</div>
				<div class="solutionPane markdownPane">
					<bean:write name="solution" property="answer" filter="false" />
				</div>
			</td>
		</tr>
	</table>
	
	<!-- Display tag list -->
	<div class="tagList">
	<strong>Tags:</strong>
		<logic:iterate name="solution" property="tags" id="tag">
			<html:link action="/search?searchType=1" paramId="searchStr"
					   paramName="tag">
				<bean:write name="tag" />
			</html:link>,
		</logic:iterate>
	</div>
	<br />
	
	<!-- Display edit solution link -->
	<div>
		<ul class="actionLinks">
			<logic:equal name="edittable" value="true">
				<li class="actionLinks">
					<html:link action="editsolution" paramId="sid"
					 paramName="solution" paramProperty="sid">
					 Edit Solution
					 </html:link>
				</li>
			</logic:equal>
			<li class="actionLinks">
				<a href="javascript:void(0)" 
				   onclick="saveSolution(<bean:write name="solution" property="sid" />, this, 'save')">
				Save Solution
				</a>
			</li>
		</ul>
	</div>
	
	<!-- Display Comments -->	
	<div class="commentPane">
		<!-- Comment form -->
		<logic:equal name="loggedIn" value="true">
		<div class="commentBox" id="newComment">
			<html:form action="ajax_postcomment" method="post">
				<input type="hidden" value="0" name="id" id="newComment_id" />
				<input type="hidden" name="parentId" id="newComment_parentId" />
				<html:hidden name="solution" property="sid" styleId="newComment_sid" />
				<textarea name="body" id="newComment_bodyInput" rows="5" cols="50" ></textarea>
				<br />
				<input type="button" value="Post Reply" onclick="submitComment('newComment')" />
			</html:form>
		</div>
		</logic:equal>
	
		<%@ include file="components/DisplayCommentThread.jsp" %>
	</div>
</div>

<!-- Includes footer -->
<%@ include file="components/Footer.jsp" %>

</body>
</html:html>