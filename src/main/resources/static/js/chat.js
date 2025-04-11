console.log("Web Socket đang chạy!")
var ws; // Global WebSocket variable

function sendMessage() {
    var messageInput = document.getElementById("messageInput");
    var message = messageInput.value.trim();
    if (message !== "") {
        // Display the sent message
        console.log("Gửi tin nhắn:", message);
        displayMessage(message, 'sender');
        ws.send(message);
        messageInput.value = ""; // Clear input
    }
}

function displayMessage(message, senderType) {
    var chatMessages = document.getElementById("chatMessages");
    var messageDiv = document.createElement("div");
    messageDiv.classList.add("message", senderType);

    // Tạo avatar
    var avatarImg = document.createElement("img");
    avatarImg.classList.add("avatar");

    // Xác định avatar dựa trên loại người gửi (sender hoặc receiver)
    if(role == 'ROLE_RECRUITER') {
        if (senderType === 'sender') {
            avatarImg.src = recruiterAvatar; // Avatar của người gửi (recruiter)
            avatarImg.alt = "Sender Avatar";
        } else {
            avatarImg.src = managerAvatar; // Avatar của người nhận (manager)
            avatarImg.alt = "Receiver Avatar";
        }
    }

    if(role == 'ROLE_MANAGER') {
        if (senderType === 'sender') {
            avatarImg.src = managerAvatar; // Avatar của người gửi (recruiter)
            avatarImg.alt = "Sender Avatar";
        } else {
            avatarImg.src = recruiterAvatar; // Avatar của người nhận (manager)
            avatarImg.alt = "Receiver Avatar";
        }
    }

    // Tạo nội dung tin nhắn
    var messageSpan = document.createElement("span");
    messageSpan.textContent = message;

    // Thêm lớp CSS cho tin nhắn
    if (senderType === 'sender') {
        messageSpan.classList.add("sender-message");
    } else {
        messageSpan.classList.add("receiver-message");
    }

    // Thêm avatar và tin nhắn vào container
    messageDiv.appendChild(avatarImg);
    messageDiv.appendChild(messageSpan);

    // Thêm tin nhắn vào khung chat
    chatMessages.appendChild(messageDiv);

    // Tự động cuộn xuống dưới cùng
    chatMessages.scrollTop = chatMessages.scrollHeight;
}

document.addEventListener("DOMContentLoaded", function () {
    ws = new WebSocket(`ws://localhost:8080/websocket/${conversationId}/${role}`);
    console.log(ws);
    ws.onopen = function () {
        console.log("WebSocket connected successfully");
        document.getElementById("messageInput").addEventListener("keypress", function (event) {
            if (event.key === "Enter") {
                sendMessage();
            }
        });
    };

    ws.onmessage = function (event) {
        console.log("Check" + event);
        displayMessage(event.data, 'receiver');
    };

    ws.onclose = function () {
        console.log("WebSocket closed");
    };

    ws.onerror = function (event) {
        console.error("WebSocket error observed:", event);
    };
});

function searchChat(input) {
    const searchValue = input.value.toLowerCase();
    const chatLists = document.querySelectorAll(".chat_list");

    chatLists.forEach(chat => {
        const workingNameElement = chat.querySelector(".col-lg-2 div");
        if (workingNameElement) {
            const workingName = workingNameElement.textContent.trim().toLowerCase();
            chat.style.display = workingName.includes(searchValue) ? "" : "none";
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {
    // Lấy giá trị ID từ URL
    const urlParams = new URLSearchParams(window.location.search);
    const currentID = urlParams.get("mid"); // Lấy giá trị mid từ URL
    console.log("cur: " + currentID);

    // Lặp qua tất cả các phần tử có class "chat-container"
    document.querySelectorAll(".chat-container").forEach(divElement => {
        const anchor = divElement.querySelector("a");
        const divHref = anchor.getAttribute("href");
        const divParams = new URLSearchParams(divHref.split("?")[1]);
        const divID = divParams.get("mid"); // Lấy giá trị mid từ href trong thẻ a
        console.log("div: " + divID);
        // So sánh ID và thêm class nếu trùng khớp
        if (currentID === divID) {
            divElement.classList.add("highlight"); // Thêm class mới
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    // Lấy giá trị ID từ URL
    const urlParams = new URLSearchParams(window.location.search);
    const currentID = urlParams.get("rid"); // Lấy giá trị mid từ URL
    console.log("cur: " + currentID);

    // Lặp qua tất cả các phần tử có class "chat-container"
    document.querySelectorAll(".chat-container1").forEach(divElement => {
        const anchor = divElement.querySelector("a");
        const divHref = anchor.getAttribute("href");
        const divParams = new URLSearchParams(divHref.split("?")[1]);
        const divID = divParams.get("rid"); // Lấy giá trị mid từ href trong thẻ a
        console.log("div: " + divID);
        // So sánh ID và thêm class nếu trùng khớp
        if (currentID === divID) {
            divElement.classList.add("highlight"); // Thêm class mới
        }
    });
});

function toggleEmojiPicker() {
    const picker = document.getElementById('emojiPicker');
    picker.style.display = picker.style.display === 'none' ? 'block' : 'none';
}

// Đóng emoji picker khi click ra ngoài
document.addEventListener('click', function (event) {
    const picker = document.getElementById('emojiPicker');
    const emojiButton = document.querySelector('button[onclick="toggleEmojiPicker()"]');
    if (!picker.contains(event.target) && event.target !== emojiButton) {
        picker.style.display = 'none';
    }
});

// Xử lý chọn emoji
const picker = document.getElementById('emojiPicker');
const input = document.getElementById('messageInput');
picker.addEventListener('emoji-click', event => {
    input.value += event.detail.unicode;
});