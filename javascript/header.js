/* Clears login username */
function clearUsername()
{
	var node = document.getElementById("usernameField");
	if(node.value.toLowerCase() == "username")
	{
		node.value = "";
		node.style.color = "black";
	}
	
}