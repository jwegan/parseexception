<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page session="false" %>
<!-- Assumptions: User is logged in and is the one that posted the comment -->
<bean:define id="cid" name="comment" property="id" />
<div id="newcbox<%= cid %>" class="commentBox markdownPane" style="padding-left: 0px;">
	 <span class="commentPoster">
	 	Posted by <html:link action="/viewuser" paramId="uid"
	 						 paramName="comment" paramProperty="uid">
	 	<bean:write name="comment" property="username" /></html:link>
	 	<bean:write name="comment" property="timestamp" />
	 	<logic:equal name="comment" property="editted" value="true">
	 		*editted*
	 	</logic:equal>
	 </span>
	 <span class="commentBody" id="newcbox<%= cid %>_body">
	 	<bean:write name="comment" property="body" filter="false"/>
	 </span>
	 
	 <span>
	 	<ul>
	 		<li class="actionLinks">
	 			<bean:define id="parentId" name="comment" property="parentId" />
	 			<bean:define id="sid" name="comment" property="sid" />

				<span id="newcbox<%= cid %>_raw" style="display: none;">
			 		<bean:write name="comment" property="bodyRaw" />
			 	</span>
	 			<html:link href="javascript:void(0)"
	 				onclick="<%= "insertEditCommentBox('newcbox" + cid + "'," +
	 							 parentId + "," + cid + "," + sid + ")"%>">
	 			edit
	 			</html:link>
	 		</li>
	 </span>
</div>