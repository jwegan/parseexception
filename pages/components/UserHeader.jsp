<div class="subHeader">
<ul class="subheaderContainer">
	<li class="subheaderCell" id="aboutCell">
		<html:link action="/viewuser" styleClass="headerLinkBlack" 
		 paramId="uid" paramName="user" paramProperty="uid">
			About
		</html:link>
	</li>
	<li class="subheaderCell" id="submittedCell">
		<html:link action="/submittedbyuser" styleClass="headerLinkBlack"
		 paramId="uid" paramName="user" paramProperty="uid">
			Solutions Posted
		</html:link>
	</li>
	
	<logic:equal name="loggedIn" value="true">
		<logic:equal name="user" property="isLoggedInUser" value="true">
			<li class="subheaderCell" id="savedCell">
				<html:link action="/savedsolutions" styleClass="headerLinkBlack">
					Solutions Saved
				</html:link>
			</li>
		</logic:equal>
	</logic:equal>
	
	<li class="subheaderCell" id="likedCell">
		<html:link action="/likedbyuser" styleClass="headerLinkBlack"
		 paramId="uid" paramName="user" paramProperty="uid">
			Solutions Liked
		</html:link>
	</li>
	<li class="subheaderCellEnd" id="commentCell">
		<html:link action="/viewusercomments" styleClass="headerLinkBlack"
		 paramId="uid" paramName="user" paramProperty="uid">
			Comments
		</html:link>
	</li>	
</ul>
</div>