var wsUri = "ws://" + document.location.host + document.location.pathname + "omicon_endpoint";
var websocket = new WebSocket(wsUri);
websocket.onerror = function(evt) {
    onError(evt);
};

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}
var output = document.getElementById("output");
var genlog = document.getElementById("genlog");
var written = 0;
var interval, notWrite = 0;
websocket.onopen = function(evt) {
    onOpen(evt);
};

function writeToScreen(message) {
    if (message.indexOf("acknowledged") > -1) {
        genlog.innerHTML = "<br>" + message + genlog.innerHTML;
        written++;
        if (written % 50==0)
            genlog.innerHTML = "";
    }
    else
        output.innerHTML = "<br>" + message + "<br>" + output.innerHTML;

}

function onOpen() {
    writeToScreen("Connected to WebSocket");
}
websocket.onmessage = function(evt) {
    onMessage(evt)
};

function sendText(json) {
    console.log("sending text: " + json);
    websocket.send(json);
}
var flg = "";
function onMessage(evt) {
    console.log("received: " + evt.data);
    if (evt.data === "sending userlist") {
        flg = "change_userlist";
    }
    else {
        if (flg === "change_userlist") {
            var splitres = evt.data.split(",");
            var option = "";
            for (i = 0; i < splitres.length; i++) {
                var secondSplit = splitres[i].split("/");
                option += "<option value='" + secondSplit[1] + "'>" + secondSplit[0] + "</option>";
            }
            $("#user_list").html(option);
            flg = "";
            sendAck(evt.data);
        }

    }
    if (notWrite === 0)
        writeToScreen(evt.data);

}
function sendacksInfiniteLoop(data) {
    notWrite = 1;
    var splitres = data.split(",");
    console.log("ack sending starting");
    for (i = 0; i < splitres.length; i++) {
        var secondSplit = splitres[i].split("/");
        websocket.send('{\"command\": \"ack\",\"text\": \"ping\",\"user\": \"' + secondSplit[0] + '\"}');

        //console.log("ack sent to " + secondSplit[0]);
    }
    console.log("ack sending ended");
    notWrite = 0;
}
function sendAck(data) {

    clearInterval(interval);
    interval = setInterval(function() {
        sendacksInfiniteLoop(data)
    }, 200000);

}
function send() {
    var text = $('#text').val();
    var command = $("#dcommands option:selected").val();
    if (text == "" && command == "") {
        writeToScreen('<p class="warning">Please enter a message and select a command');
        return;
    }
    try {
        websocket.send('{\"command\": \"' + command + '\",\"text\": \"' + text + '\",\"user\": \"' + $("#user_list option:selected").text() + '\"}');
        writeToScreen('<p class="event">Sent: ' +
                '{\"command\": \"' + command + '\",\"text\": \"' + text + '\",\"user\": \"' + $("#user_list option:selected").text() + '\"}');

    } catch (exception) {
        writeToScreen('<p class="warning">');
    }
    $('#text').val("");
}
$('#send').click(function() {
    send();
});
$('#btnclear').click(function() {
    websocket.send('{\"command\": "userList",\"text\": "sad",\"user\": " "}');
    $("#output").html("");
    $("#genlog").html("");
    
});
$('#getList').click(function() {
    writeToScreen('<p class="warning">Fetching userList</p>');
    websocket.send('{\"command\": "userList",\"text\": "sad",\"user\": " "}');
});
$('#text').keypress(function(event) {
    if (event.keyCode == 13) {
        send();
    }
});