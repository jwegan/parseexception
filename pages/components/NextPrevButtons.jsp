<div class="nextPrev">
	<logic:present name="prev">
		<html:link action="<%= (String) request.getAttribute("actionURL") %>" paramId="pageNum" paramName="prev">
		prev
		</html:link>
	</logic:present>
	<logic:present name="next">
		<html:link action="<%= (String) request.getAttribute("actionURL") %>" paramId="pageNum" paramName="next">
		next
		</html:link>
	</logic:present>
</div>