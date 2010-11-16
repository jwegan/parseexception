<%--
  ~ Copyright 2006-2008 Sxip Identity Corporation
  --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ page session="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>OpenID HTML FORM Redirection</title>
</head>
<body onload="document.forms['openid-form-redirection'].submit();">
    <form name="openid-form-redirection" action="<bean:write name="message" property="OPEndpoint" />" method="post" accept-charset="utf-8">
        <logic:iterate name="message" property="parameterMap" id="element">
        	<input type="hidden" name="<bean:write name="element" property="key" />" value="<bean:write name="element" property="value" />"/>
        </logic:iterate>
        <button type="submit">Continue...</button>
    </form>
</body>
</html>
