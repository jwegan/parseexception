<div class="commentList">
<logic:iterate name="comments" id="comment" indexId="commentIdx">
	<div class="commentBox">
		 <span class="solutionTitleSmall">
		 	<html:link action="/viewsolution" paramId="sid" paramName="comment"
		 			   paramProperty="sid">
		 		<bean:write name="comment" property="solutiontitle" />
		 	</html:link>
		 	<br />
		 </span>
		 <span class="commentBody markdownPane">
		 	<bean:write name="comment" property="body" filter="false"/>
		 </span>
		 <span class="commentPoster">
		 	Posted by <html:link action="/viewuser" paramId="uid"
		 						 paramName="comment" paramProperty="uid">
		 	<bean:write name="comment" property="username" /></html:link>
		 	<bean:write name="comment" property="timestamp" />
		 	<logic:equal name="comment" property="editted" value="true">
		 		*editted*
		 	</logic:equal>
		 	<br />
		 		<html:link action="/viewcomment" paramId="id" paramName="comment"
		 			   paramProperty="id" >view
		 		</html:link>
		 	<br /><br />
		 </span>
	</div>
</logic:iterate>
</div>

<%@ include file="NextPrevButtons.jsp" %>