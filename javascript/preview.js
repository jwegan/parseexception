//
// Register for onload
//
window.onload = startGui;

//
// Globals
//

var converter;
var convertTextTimer, processingTime;
var lastText, lastOutput, lastRoomLeft;
var inputPane, previewPane;
var maxDelay = 3000; // longest update pause (in ms)

var guiStarted = false;

function startGui() {

	if (guiStarted) {
		return;
	}
	guiStarted = true;

	// find elements
	inputPane = document.getElementById("inputPane");
	previewPane = document.getElementById("previewPane");

	// First, try registering for keyup events
	// (There's no harm in calling onInput() repeatedly)
	window.onkeyup = inputPane.onkeyup = onInput;

	// In case we can't capture paste events, poll for them
	var pollingFallback = window.setInterval(function() {
		if (inputPane.value != lastText)
			onInput();
	}, 1000);

	// Try registering for paste events
	inputPane.onpaste = function() {
		// It worked! Cancel paste polling.
		if (pollingFallback != undefined) {
			window.clearInterval(pollingFallback);
			pollingFallback = undefined;
		}
		onInput();
	}

	// Try registering for input events (the best solution)
	if (inputPane.addEventListener) {
		// Let's assume input also fires on paste.
		// No need to cancel our keyup handlers;
		// they're basically free.
		inputPane.addEventListener("input", inputPane.onpaste, false);
	}

	// build the converter
	converter = new Showdown.converter();

	// do an initial conversion to avoid a hiccup
	convertText();

	// give the input pane focus
	inputPane.focus();

	// start the other panes at the top
	// (our smart scrolling moved them to the bottom)
	previewPane.scrollTop = 0;
}

//
// Conversion
//

function convertText() {
	// get input text
	var text = inputPane.value;

	// if there's no change to input, cancel conversion
	if (text && text == lastText) {
		return;
	} else {
		lastText = text;
	}

	var startTime = new Date().getTime();

	// Do the conversion
	text = text.replace(/\n/g, "  \n"); 
	text = text.replace(/(\s)(http:\/\/[\S]+)(\s)/g, "$1[$2]($2)$3");
	
	text = text.replace(/([a-zA-Z0-9])_([a-zA-Z0-9])/g, "$1\\_$2");
	text = text.replace(/([a-zA-Z0-9])__([a-zA-Z0-9])/g, "$1\\_\\_$2");
	text = text.replace(/([a-zA-Z0-9])\*([a-zA-Z0-9])/g, "$1\\*$2");
	text = text.replace(/([a-zA-Z0-9])\*\*([a-zA-Z0-9])/g, "$1\\*\\*$2");

	text = converter.makeHtml(text);
	
	text = text.replace(/<code>/g, '<code class="prettyprint">');
	text = text.replace(/\n\n/g, "<br/>");

	// display processing time
	var endTime = new Date().getTime();
	processingTime = endTime - startTime;

	// save proportional scroll positions
	saveScrollPositions();

	previewPane.innerHTML = text;
    prettyPrint();
	lastOutput = text;

	// restore proportional scroll positions
	restoreScrollPositions();
};

//
// Event handlers
//

function onConvertTextSettingChanged() {
	// If the user just enabled automatic
	// updates, we'll do one now.
	onInput();
}

function onConvertTextButtonClicked() {
	// hack: force the converter to run
	lastText = "";

	convertText();
	inputPane.focus();
}

function onInput() {
	// In "delayed" mode, we do the conversion at pauses in input.
	// The pause is equal to the last runtime, so that slow
	// updates happen less frequently.
	//
	// Use a timer to schedule updates. Each keystroke
	// resets the timer.

	// if we already have convertText scheduled, cancel it
	if (convertTextTimer) {
		window.clearTimeout(convertTextTimer);
		convertTextTimer = undefined;
	}

	var timeUntilConvertText = 0;
		// make timer adaptive
		timeUntilConvertText = processingTime;

	if (timeUntilConvertText > maxDelay)
		timeUntilConvertText = maxDelay;

	// Schedule convertText().
	// Even if we're updating every keystroke, use a timer at 0.
	// This gives the browser time to handle other events.
	convertTextTimer = window.setTimeout(convertText, timeUntilConvertText);
}

//
// Smart scrollbar adjustment
//
// We need to make sure the user can't type off the bottom
// of the preview and output pages. We'll do this by saving
// the proportional scroll positions before the update, and
// restoring them afterwards.
//

var previewScrollPos;
var outputScrollPos;

function getScrollPos(element) {
	// favor the bottom when the text first overflows the window
	if (element.scrollHeight <= element.clientHeight)
		return 1.0;
	return element.scrollTop / (element.scrollHeight - element.clientHeight);
}

function setScrollPos(element, pos) {
	element.scrollTop = (element.scrollHeight - element.clientHeight) * pos;
}

function saveScrollPositions() {
	previewScrollPos = getScrollPos(previewPane);
}

function restoreScrollPositions() {
	// hack for IE: setting scrollTop ensures scrollHeight
	// has been updated after a change in contents
	previewPane.scrollTop = previewPane.scrollTop;

	setScrollPos(previewPane, previewScrollPos);
}

//
// Textarea resizing
//
// Some browsers (i.e. IE) refuse to set textarea
// percentage heights in standards mode. (But other units?
// No problem. Percentage widths? No problem.)
//
// So we'll do it in javascript. If IE's behavior ever
// changes, we should remove this crap and do 100% textarea
// heights in CSS, because it makes resizing much smoother
// on other browsers.
//

function getTop(element) {
	var sum = element.offsetTop;
	while (element = element.offsetParent)
		sum += element.offsetTop;
	return sum;
}

function getWindowHeight(element) {
	if (window.innerHeight)
		return window.innerHeight;
	else if (document.documentElement && document.documentElement.clientHeight)
		return document.documentElement.clientHeight;
	else if (document.body)
		return document.body.clientHeight;
}
