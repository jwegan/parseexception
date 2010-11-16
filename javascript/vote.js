/* This method submits a vote */
function submitVote(sid, clicked)
{
	clicked.onclick = null;
	
	var vote = 3; // Default is to erase vote
	if(clicked.src.indexOf("/images/uparrow_black.png") >= 0)
	{
		vote = 1;
	}else if(clicked.src.indexOf("/images/downarrow_black.png") >= 0)
	{
		vote = 2;
	}
	
	// Create POST str
	var poststr = "sid=" + encodeURI(sid);
	poststr += "&vote=" + encodeURI(vote);
	
	// Create request obj
	xmlhttp=null;
	if (window.XMLHttpRequest)
	{
		// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	// Send request
	xmlhttp.onreadystatechange=
		function(){ processVoteSubmit(xmlhttp, clicked, sid); };
	xmlhttp.open("POST","ajax_submitvote.do",true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.setRequestHeader("Content-length", poststr.length);
	//xmlhttp.setRequestHeader("Connection", "close");
	xmlhttp.send(poststr);	
}

/* Handle response */
function processVoteSubmit(xmlhttp, clicked, sid)
{
	if(xmlhttp.readyState == 4)
	{
		var response = xmlhttp.responseText.replace(/\s/g, "");
		if(xmlhttp.status != 200 || response == "ERROR")
		{
			alert("Sorry, there was an error submitting your vote.");
		}else if(response == "LOGIN")
		{
			alert("You must be logged in to vote!");
		}else if(response == "SUCCESS")
		{
			scoreNode = document.getElementById("score");
			uparrow = document.getElementById("uparrow");
			downarrow = document.getElementById("downarrow");
			if(clicked.src.indexOf("/images/uparrow_green.png") >= 0)
			{
				scoreNode.innerHTML = Number(scoreNode.innerHTML) - 1;
				clicked.src = "/images/uparrow_black.png";
			}else if(clicked.src.indexOf("/images/uparrow_black.png") >= 0)
			{
				if(downarrow.src.indexOf("/images/downarrow_red.png") >= 0)
				{
					scoreNode.innerHTML = Number(scoreNode.innerHTML) + 1;
					downarrow.src = "/images/downarrow_black.png";
				}
				
				scoreNode.innerHTML = Number(scoreNode.innerHTML) + 1;
				clicked.src = "/images/uparrow_green.png";
			}else if(clicked.src.indexOf("/images/downarrow_red.png") >= 0)
			{
				scoreNode.innerHTML = Number(scoreNode.innerHTML) + 1;
				clicked.src = "/images/downarrow_black.png";
			}else if(clicked.src.indexOf("/images/downarrow_black.png") >= 0)
			{
				if(uparrow.src.indexOf("/images/uparrow_green.png") >= 0)
				{
					scoreNode.innerHTML = Number(scoreNode.innerHTML) - 1;
					uparrow.src = "/images/uparrow_black.png";
				}
				
				scoreNode.innerHTML = Number(scoreNode.innerHTML) - 1;
				clicked.src = "/images/downarrow_red.png";
			}
			
			clicked.onclick = function(){ submitVote(sid, clicked); };
		}
	}
}

/* Process Saving Solution */
function saveSolution(sid, link, action)
{
	link.onclick = null;
	link.style.color = "black";
	
	// Create POST str
	var poststr = "sid=" + encodeURI(sid);
	poststr += "&action=" + encodeURI(action);
	
	// Create request obj
	xmlhttp=null;
	if (window.XMLHttpRequest)
	{
		// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	// Send request
	xmlhttp.onreadystatechange=processSaveResponse;
	xmlhttp.open("POST","ajax_save.do",true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.setRequestHeader("Content-length", poststr.length);
	//xmlhttp.setRequestHeader("Connection", "close");
	xmlhttp.send(poststr);	
}

/* Handle response */
function processSaveResponse()
{
	if(xmlhttp.readyState == 4)
	{
		var response = xmlhttp.responseText.replace(/\s/g, "");
		if(xmlhttp.status != 200 || response == "ERROR")
		{
			alert("Sorry, there was an error removing the solution.");
		}else if(response == "LOGIN")
		{
			alert("You must be logged in to vote!");
		}else if(response == "SUCCESS")
		{
			// Do nothing
		}
	}
}
