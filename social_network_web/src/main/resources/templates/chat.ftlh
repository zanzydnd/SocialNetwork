<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
    <script>
        var stompClient = null;

        function connect() {
            var socket = new SockJS("/chat");
            var senderId = document.getElementById("userId").value;
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log("Connected: " + frame);
                stompClient.subscribe("/user/" + senderId + "/queue/messages", function (messageOutput) {
                    showMessageOutput(JSON.parse(messageOutput.body));
                });
            });
        }

        function disconnect() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
        }

        function sendMessage() {
            var text = document.getElementById("message").value;
            var message = {
                senderId: document.getElementById("userId").value,
                recipientId: document.getElementById("recipientId").value,
                text: text,
                senderFirstName: document.getElementById("userName").value,
                chatId: document.getElementById("userId").value + "_" + document.getElementById("recipientId").value
            };
            stompClient.send("/app/chat", {}, JSON.stringify(message));
            appendSentMessage(message);
        }

        function appendSentMessage(message) {
            var sent = document.getElementById("sent");
            var p = document.createElement("p");
            p.style.wordWrap = "break-word";
            p.style.color = "black";
            p.appendChild(document.createTextNode(message.senderFirstName + ": " + message.text));
            sent.appendChild(p);
        }

        function showMessageOutput(messageOutput) {
            var response = document.getElementById("response");
            var p = document.createElement("p");
            p.style.wordWrap = "break-word";
            p.style.color = "black";
            p.appendChild(document.createTextNode(messageOutput.senderFirstName + ": " + messageOutput.text));
            response.appendChild(p);
        }

        window.onload = connect;
    </script>
</head>
<body>
<h1>Chat with ${recipient.firstName}</h1>

<div class="chat">
    <#if messages??>
        <#list messages as message>
            <#if message.senderId == user.id>
                <a STYLE="color: #19bc8b">
                    ${user.firstName} : ${message.text}
                </a>
            <#else>
                <a STYLE="color: black">
                    ${message.senderFirstName} : ${message.text}
                </a>
            </#if>
        </#list>
    </#if>
</div>

<div class="new_messages">
    <div class="sent" id="sent">

    </div>
    <div class="recieved" id="response">

    </div>
</div>


<input type="hidden" id="userId" value="${user.id}">
<input type="hidden" id="recipientId" value="${recipient.id}">
<input type="hidden" id="userName" value="${user.firstName}">
<input type="text" placeholder="Ваше сообщение" name="message" id="message">
<button id="send" onclick="sendMessage()">Отправить</button>

</body>
</html>

