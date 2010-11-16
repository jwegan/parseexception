
<div class="sidePanel">
	<div id="siteSearch">
		<h3>Site Search:</h3>
		<html:form action="search">
			<html:text property="searchStr" styleId="searchField"/><br />
			Search Type:
			<html:select property="searchType">
				<html:option value="0">Normal Search</html:option>
				<html:option value="1">Tag Search</html:option>
				<html:option value="2">User Search</html:option>
			</html:select>
			<html:submit value="Search" />
		</html:form>
	</div>
		
	
	<br /><br />
	<html:form action="editsolution">
		<html:submit styleId="postButton">Post new solution</html:submit>
	</html:form>
</div>