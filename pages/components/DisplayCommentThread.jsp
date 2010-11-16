<script type="text/javascript" src="javascript/comment.js"></script>

<% int curIndex = 0; %>

<logic:iterate name="thread" id="comment" indexId="commentIdx">
	<div class="commentBox" id="cbox<%= Integer.toString(curIndex) %>"
		 style="margin-left: <bean:write name="comment" property="indent" />px;">
		 <span class="commentPoster">
		 	Posted by <html:link action="/viewuser" paramId="uid"
		 						 paramName="comment" paramProperty="uid">
		 	<bean:write name="comment" property="username" /></html:link>
		 	<bean:write name="comment" property="timestamp" />
		 	<logic:equal name="comment" property="editted" value="true">
		 		*editted*
		 	</logic:equal>
		 </span>
		 <span class="commentBody markdownPane" id="cbox<%= Integer.toString(curIndex) %>_body">
		 	<bean:write name="comment" property="body" filter="false"/>
		 </span>
		 
		 <logic:equal name="loggedIn" value="true">
		 <span>
		 	<ul>
		 		<li class="actionLinks">
		 			<bean:define id="parentId" name="comment" property="parentId" />
		 			<bean:define id="cid" name="comment" property="id" />
		 			<bean:define id="sid" name="comment" property="sid" />

		 			<logic:equal name="comment" property="editable" value="true">
			 			<span id="cbox<%= Integer.toString(curIndex) %>_raw" style="display: none;">
			 				<bean:write name="comment" property="bodyRaw" />
			 			</span>
			 			<html:link href="javascript:void(0)"
			 				onclick="<%= "insertEditCommentBox('cbox" + 
			 							Integer.toString(curIndex++) + "'," +
			 							 parentId + "," + cid + "," + sid + ")"%>">
			 				edit
		 				</html:link>
		 			</logic:equal>
		 			
		 			<logic:equal name="comment" property="editable" value="false">
			 			<html:link href="javascript:void(0)"
			 				onclick="<%= "insertNewCommentBox('cbox" + 
			 							Integer.toString(curIndex++) + "'," +
			 							 cid + "," + sid + ")"%>">
			 			reply
			 			</html:link>
		 			</logic:equal>
		 		</li>
		 </span>
		 </logic:equal>
	</div>
</logic:iterate>