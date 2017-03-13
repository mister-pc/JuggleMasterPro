
function init() {
	window.onerror = noError;
	window.defaultStatus = "";
	top.defaultStatus = "";
	top.status = "";

	document.onmousedown = noSelection;
	if (typeof document.onselectstart != "undefined") {
		document.onselectstart = noSelection;
	}
}

function noError() {
	return true;
}

function noSelection() {
	return false;
}

function writeStatus(text) {
	top.status = text;
}

function catchKeyPressed(keyEvent) {
	var eventKeyCode = "";
	if (keyEvent.keyCode) {
		eventKeyCode = keyEvent.keyCode;
	} else if (keyEvent.which) {
		eventKeyCode = keyEvent.which;
	} else {
		return;
	}
	if (String.fromCharCode(eventKeyCode).toLowerCase() == 'j') {
		window.location.replace('http://jugglemaster.free.fr/');
    }
}

