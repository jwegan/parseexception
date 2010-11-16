<%
	String url = (String) request.getAttribute("actionURL");
	String tag = "";
	int idx = url.indexOf("tag=");
	if(idx >= 0)
	{
		tag = url.substring(idx + "tag=".length());
		url = url.substring(0, idx - 1);
	}
	
	idx = url.indexOf("?");
	if(idx >= 0)
	{
		url = url.substring(0, idx) + ".do" + url.substring(idx);
	}else
	{
		url = url + ".do";
	}
%>

<form action="<%= url %>" method="GET">
Filter by tag: <input type="text" name="tag" class="formField" value="<%= tag %>" />
</form>