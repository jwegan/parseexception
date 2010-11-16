<style type="text/css">
.solutionTable {
	float: inherit;
}

.solutionRow {
	margin: 5px 0 5px;
}
</style>

<div>
<table class="solutionTable">
	<logic:iterate name="solutions" id="solution" indexId="solutionIdx">
		<tr class="solutionRow">
			<td>
				<div class="solutionScore">
					<bean:write name="solution" property="score" />
				</div>
			</td>
			<td>
				<html:link action="/viewsolution" paramId="sid" styleClass="solutionTitle" 
						   paramName="solution" paramProperty="sid">
				<bean:write name="solution" property="question" />
				</html:link><br />
				<div class="solutionSubmission">
					Submitted by: 
					<html:link action="/viewuser" paramId="uid" paramName="solution"
							   paramProperty="uid">
							   <bean:write name="solution" property="uname" /> 
					</html:link>
					<bean:write name="solution" property="timestamp" />
				</div>
				
				<div>
					<ul class="actionLinks">
						<logic:equal name="actionURL" value="/savedsolutions">
							<li class="actionLinks">
								<a href="javascript:void(0)" 
									onclick="saveSolution(<bean:write name="solution" property="sid" />, this, 'remove')">
									Remove
								</a>
							</li>
						</logic:equal>
					</ul>
				</div>
				
			</td>
		</tr>
	</logic:iterate>
</table>	
</div>

<%@ include file="NextPrevButtons.jsp" %>