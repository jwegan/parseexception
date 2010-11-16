<style type="text/css">
.userList {
	list-style-type: none;
	font-size: small;
}
</style>

<div>
	<ul class="userList">
		<logic:iterate name="users" id="thisuser" indexId="userIdx">
			<li>
				<html:link action="/viewuser" paramId="uid" paramName="thisuser"
						   paramProperty="uid">
				<bean:write name="thisuser" property="username" />
				</html:link>
			</li>
		</logic:iterate>
	</ul>
</div>

<%@ include file="NextPrevButtons.jsp" %>