/* This method creates a comment form box */
function createCommentBox(parentId,cid,sid,formName)
{
	// Create the container
	var container = document.createElement("div");
	container.className = "commentBox";
	container.id = formName;

	// Create the form
	var form = document.createElement("form");
	form.action = "ajax_postcomment.do";
	form.method = "post";
	container.appendChild(form);
	
	// Comment Id
	var commentId = document.createElement("input");
	commentId.id = formName + "_id";
	commentId.type = "hidden";
	commentId.value = cid;
	commentId.name="id";
	form.appendChild(commentId);
	
	// Parent Id
	var pId = document.createElement("input");
	pId.id = formName + "_parentId"
	pId.type = "hidden";
	pId.name = "parentId";
	pId.value = parentId;
	form.appendChild(pId);
	
	// solution id
	var solution = document.createElement("input");
	solution.id = formName + "_sid"
	solution.type = "hidden";
	solution.name = "sid";
	solution.value = sid;
	form.appendChild(solution);
	
	// Body
	var textarea = document.createElement("textarea");
	textarea.id = formName + "_bodyInput";
	textarea.rows = "5";
	textarea.cols="50";
	textarea.name="body";
	form.appendChild(textarea);
	
	// Submit button
	form.appendChild(document.createElement("br"));
	var submit = document.createElement("input");
	submit.type = "button";
	submit.value = "Post Reply";
	submit.onclick = function() { submitComment(formName); };
	form.appendChild(submit);

	// Cancel button
	var cancel = document.createElement("input");
	cancel.type = "button";
	cancel.value = "Cancel";
	cancel.onclick = function() { removeCommentBox(formName); };
	form.appendChild(cancel);
	
	return container;
}

/* This method submits a comment form */
function submitComment(boxId)
{	
	// Create POST str
	var poststr = "id=" + encodeURI(document.getElementById(boxId + "_id").value);
	poststr += "&parentId=" + encodeURI(document.getElementById(boxId + "_parentId").value);
	poststr += "&sid=" + encodeURI(document.getElementById(boxId + "_sid").value);
	poststr += "&body=" + encodeURI(document.getElementById(boxId + "_bodyInput").value);
	
	// Clear form
	document.getElementById(boxId + "_bodyInput").value = "";
	
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
		function(){ processCommentSubmit(xmlhttp, boxId); };
	xmlhttp.open("POST","ajax_postcomment.do",true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.setRequestHeader("Content-length", poststr.length);
	//xmlhttp.setRequestHeader("Connection", "close");
	xmlhttp.send(poststr);
	
}

/* Handle response */
function processCommentSubmit(xmlhttp, boxId)
{
	if(xmlhttp.readyState == 4 && xmlhttp.status == 200)
	{
		var node = document.getElementById(boxId);
		var id = document.getElementById(boxId + "_id");
		
		if(id != null && id.value != "0")
		{
			node.nextSibling.style.display = "block";
			node.nextSibling.innerHTML = xmlhttp.responseText;
			node.parentNode.removeChild(node);
			prettyPrint();
		}else{
			node.innerHTML = xmlhttp.responseText;
			//node.style.marginLeft = "0px";
		}
	}
}

/* This method removes a comment form box */
function removeCommentBox(boxId)
{
	var node = document.getElementById(boxId);
	var id = document.getElementById(boxId + "_id");
	
	if(id != null && id.value != "0")
	{
		node.nextSibling.style.display = "block";
	}
	
	if(node != null)
	{
		node.parentNode.removeChild(node);
	}
}

/* Create a comment box to edit and existing comment */
function insertEditCommentBox(htmlId, parentId, cid, sid)
{
	// Verify box doesn't already exist
	var formName = htmlId + "_edit";
	var oldBox = document.getElementById(formName);
	if(oldBox != null)
	{
		oldBox.parentNode.removeChild(oldBox);
	}
	
	// Insert the comment box
	var form = createCommentBox(parentId, cid, sid, formName);
	var comment = document.getElementById(htmlId);
	form.style.marginLeft = comment.style.marginLeft;
	comment.style.display = "none";
	comment.parentNode.insertBefore(form, comment);
	
	// Set the comment text
	var cbody = document.getElementById(htmlId + "_raw");
	var textarea = document.getElementById(formName + "_bodyInput");
	textarea.value = processCommentString(cbody.innerHTML);
}


/* Create comment box for a new comment */
function insertNewCommentBox(htmlParentId,parentId,sid)
{
	// Verify box doesn't already exist 
	var formName = htmlParentId + "_reply";
	var oldBox = document.getElementById(formName);
	if(oldBox != null)
	{
		oldBox.parentNode.removeChild(oldBox);
	}
	
	var container = createCommentBox(parentId, "0", sid, formName);
	
	// Add to the document
	var root = document.getElementById(htmlParentId);
	root.appendChild(container);
}

/* process comment string */
function processCommentString(string)
{
	// Substitute in html replacements
	/*
	string = string.replace("<b>", "[b]");
	string = string.replace("</b>", "[/b]");
	string = string.replace("<br />", "\n");
	string = string.replace("<a href=([^>])>", "[link url=$1]");
	string = string.replace("</a>", "[/link]");
	string = string.replace("&lt;", "<");
	string = string.replace("&gt;", ">");
	*/
	
	// trim
	var start = -1;
	var end = -1;
	var i = 0;
	for(i = 0; i < string.length; i++)
	{
		if(string.charAt(i) != ' ' && string.charAt(i) != '\n' &&
		   string.charAt(i) != '\t')
		{
			end = i;
			
			if(start < 0)
			{
				start = i;
			}
		}
	}
	
	if(start > 0 && end > 0)
	{
		string = string.substring(start, end+1);
	}
	
	return string;
}