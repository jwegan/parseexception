<script type="text/javascript" src="javascript/header.js">
</script>

<div class="pageHeader">
	<strong class="siteTitle">
		<html:link action="viewhome" styleClass="headerLink">
		ParseException
		</html:link>
	</strong>
	
	<!-- Display links for logged in user -->
	<logic:equal name="loggedIn" value="true">
		<table class="linkTable">
			<tr>
				<td class="linkCell">
					<html:link action="/submittedbyuser" paramId="uid" 
							   paramName="curUser" paramProperty="uid"
								styleClass="headerLink">
					<strong><bean:write name="curUser" property="username" /></strong>
					</html:link>
				</td>
				
				<td class="linkCell">
					<html:link action="/editsettings" styleClass="headerLink">
					Settings
					</html:link>
				</td>
				<td class="linkCell">
					<html:link action="/viewinbox" styleClass="headerLink">
					<logic:present name="unreadCount">
						<strong>
						Inbox(<bean:write name="unreadCount" />)
						</strong>
					</logic:present>
					<logic:notPresent name="unreadCount">
						Inbox
					</logic:notPresent>
					</html:link>
				</td>
				<td class="linkCell">
					<html:link action="/viewmyfriends" styleClass="headerLink">
					Friends
					</html:link>
				</td>
				<td class="linkCell">
					<html:link action="/logout" styleClass="headerLink">
					Logout
					</html:link>
				</td>
			</tr>
		</table>
	</logic:equal>
	
	<!-- Display loggin form for non-loggedin user -->
	<logic:equal name="loggedIn" value="false">
		<table class="linkTable">
			<tr>
				<td>
					<html:link href="/pages/login.jsp">
						<img src="/images/openid_tiny.png" class="imageLink" />
					</html:link>
				</td>
				<td style="padding-top: 2px;">
					<html:link href="/pages/login.jsp" styleClass="headerLink">Login</html:link> 
				</td>
			</tr>
		</table>
	</logic:equal>
</div>

