<%@ page import="com.parseexception.Config" %>
<style type="text/css">

.footer
{
	width: 100%;
	float: left;
}

.footerLinks
{
	font-size: small;
	padding: 5px;
	margin-left: auto;
	margin-right: auto;
	width: 600px;
}

.footerCell
{
	padding: 1px 15px;
	border-right-color: LightGrey;
	border-right-style: solid;
	border-right-width: thin;
	display: inline;
}

.footerCellEnd
{
	padding: 1px 15px;
	display: inline;
}

.termsAndCopyright
{
	font-size:x-small;
	color:gray;
	margin-left:5px;
	margin-left: auto;
	margin-right: auto;
	width: 680px;
}
</style>

<div class="footer">
<div class="footerLinks">
	<ul>
		<li class="footerCell">
			<html:link page="/pages/footer/about.jsp">
				About <%= Config.siteName %>
			</html:link>
		</li>
		<li class="footerCell">
			<html:link page="/pages/footer/faq.jsp">
				FAQ
			</html:link>
		</li>
		<li class="footerCell">
			<html:link page="/pages/footer/contact.jsp">
				Feedback/Contact Us
			</html:link>
		</li>
		<li class="footerCellEnd">
			<html:link page="/pages/footer/contact.jsp?reportError=1">
				Report a Problem
			</html:link>
		</li>
	</ul>
</div>
<div class="termsAndCopyright">
Use of this site constitutes acceptance of the 
<html:link page="/pages/footer/terms.jsp">Terms of Use</html:link> and 
<html:link page="/pages/footer/privacy.jsp">Privacy Policy</html:link>.
(c) 2010 John Egan. All rights reserved.
</div>
</div>