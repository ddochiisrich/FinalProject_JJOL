$(document).ready(function() {
    const username = $('div.chat-container').attr('data-username');
    console.log("Username from data attribute: " + username);

    const websocket = new WebSocket("ws://localhost:8080/ws/chat");

    websocket.onopen = function(evt) {
        console.log("Connected to WebSocket.");
        let entranceMessage = username + "님 입장";
        websocket.send("entrance:" + username + ":" + entranceMessage);
    };

    websocket.onmessage = function(msg) {
        console.log("Received message:", msg.data);
        onMessage(msg);
    };

    websocket.onclose = function(evt) {
        console.log("Disconnected from WebSocket.");
        console.log("WebSocket closed:", evt);
    };

    websocket.onerror = function(evt) {
        console.log("WebSocket error:", evt);
    };

    function onMessage(msg) {
        var data = msg.data;
        var sessionId = null;
        var message = null;
        var type = null;
        var arr = data.split(":");

        if (arr[0] === "entrance") {
            type = "entrance";
            sessionId = arr[1];
            message = arr.slice(2).join(":");
        } else if (arr[0] === "message") {
            type = "message";
            sessionId = arr[1];
            message = arr.slice(2).join(":");
        }

        console.log("sessionID : " + sessionId);
        console.log("message : " + message);

        if (type === "entrance") {
            var str = "<div class='chat-msg_box text-center fw-lighter'>";
            str += "<b>" + message + "</b>";
            str += "</div></div>";
            $("#chat-msgArea").append(str);
        } else if (type === "message") {
            if (sessionId === username) {
                var str = "<div class='chat-msg_box'>";
                var str1 = "<p class='text-end'>" + sessionId + "</p>";
                str += "<div class='alert alert-light lh-base fw-light fs-6 text-end'>";
                str += "<b>" + message + "</b>";
                str += "</div></div>";
                $("#chat-msgArea").append(str1, str);
            } else {
                var str = "<div class='chat-msg_box'>";
                var str1 = "<p>" + sessionId + "</p>";

                if (message && message.includes("님이 나가셨습니다.")) {
                    var formattedMessage = sessionId + " " + message;
                    str += "<div class='alert alert-warning lh-base fw-light fs-6'>";
                    str += "<b>" + formattedMessage + "</b>";
                    str += "</div>";
                } else {
                    var formattedMessage = sessionId + (message ? " : " + message : "");
                    str += "<div class='alert alert-warning lh-base fw-light fs-6'>";
                    str += "<b>" + formattedMessage + "</b>";
                    str += "</div>";
                }

                str += "</div>";
                $("#chat-msgArea").append(str1, str);
            }
        }

        var $msgArea = $("#chat-msgArea");
        $msgArea.scrollTop($msgArea[0].scrollHeight);
    }

    $("#chat-button-send").on("click", function() {
        send();
    });

    $("#chat-msg").on("keypress", function(e) {
        if (e.which == 13) {
            send();
        }
    });

    function send() {
        let msg = $("#chat-msg").val();
        if (msg.trim() !== "") {
            websocket.send("message:" + username + ":" + msg);
            $("#chat-msg").val('');
        }
    }

    $("#chat-chatout").on("click", function() {
        disconnect();
    });

    function disconnect() {
        var exitMessage = username + "님이 나가셨습니다.";
        if (websocket.readyState === WebSocket.OPEN) {
            websocket.send("exit:" + username + ":" + exitMessage);
        }
        websocket.close();
        window.location.href = "/";
    }
});
