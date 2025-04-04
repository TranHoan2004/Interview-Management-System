
// Hàm bật/tắt chatbot
function toggleChatBot() {
    const chatWindow = document.getElementById('coze-bot');
    if (chatWindow.style.display === 'block') {
        chatWindow.style.display = 'none';
    } else {
        chatWindow.style.display = 'block';
    }
}
function toggleSidebar() {
    const sidebar = document.getElementById('sidebar');
    sidebar.classList.toggle('open');
}

document.addEventListener('click', function (event) {
    const sidebar = document.getElementById('sidebar');
    const menuIcon = document.querySelector('.menu-icon');
    const isClickInsideSidebar = sidebar.contains(event.target);
    const isClickOnMenuIcon = menuIcon.contains(event.target);

    if (!isClickInsideSidebar && !isClickOnMenuIcon) {
        sidebar.classList.remove('open');
    }
});



function checkStatusToEdit(element) {
    const status = element.getAttribute('data-status');
    const editUrl = element.getAttribute('data-edit-url');

    if (status == 1 || status === "1") {
        window.location.href = editUrl;
    } else {
        alert("Editing is not allowed because the status is invalid.");
    }
}

function logout() {
    window.location.href = "/logout";
}

// Chat functions
function toggleChatList() {
    const chatList = document.getElementById('chatList');
    const chatWindow = document.getElementById('chatWindow');

    if (chatWindow.style.display === 'flex') {
        chatWindow.style.display = 'none';
    }

    if (chatList.style.display === 'flex') {
        chatList.style.display = 'none';
    } else {
        chatList.style.display = 'flex';
    }
}

function openChatWindow(name, recruiterId, managerId, chatId, role) {
    const chatWindow = document.getElementById('chatWindow');
    const chatList = document.getElementById('chatList');
    const chatWithName = document.getElementById('chatWithName');

    chatWithName.textContent = name + chatId;
    chatList.style.display = 'none';
    chatWindow.style.display = 'flex';

    const chatBody = document.getElementById('chatBody');
    chatBody.innerHTML = `
            <div class="chat-message message-received">
                <div class="message-content received">Hello! This is the start of your conversation with ${name} & ${chatId}.</div>
                <div class="message-time">${getCurrentTime()}</div>
            </div>
        `;
}

function toggleChatWindow() {
    const chatWindow = document.getElementById('chatWindow');
    if (chatWindow.style.display === 'flex') {
        chatWindow.style.display = 'none';
    } else {
        chatWindow.style.display = 'flex';
    }
}

function sendMessage() {
    const input = document.getElementById('chatInput');
    const message = input.value.trim();
    if (message) {
        const chatBody = document.getElementById('chatBody');

        // Add sent message
        const sentMessageDiv = document.createElement('div');
        sentMessageDiv.className = 'chat-message message-sent';
        sentMessageDiv.innerHTML = `
                <div class="message-content sent">${message}</div>
                <div class="message-time">${getCurrentTime()}</div>
            `;
        chatBody.appendChild(sentMessageDiv);

        input.value = '';

        // Scroll to bottom
        chatBody.scrollTop = chatBody.scrollHeight;

        // Simulate reply after 1 second
        setTimeout(() => {
            const replyMessageDiv = document.createElement('div');
            replyMessageDiv.className = 'chat-message message-received';
            replyMessageDiv.innerHTML = `
                    <div class="message-content received">Thanks for your message! We'll get back to you soon.</div>
                    <div class="message-time">${getCurrentTime()}</div>
                `;
            chatBody.appendChild(replyMessageDiv);
            chatBody.scrollTop = chatBody.scrollHeight;
        }, 1000);
    }
}

function getCurrentTime() {
    const now = new Date();
    let hours = now.getHours();
    let minutes = now.getMinutes();
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0' + minutes : minutes;
    return `${hours}:${minutes} ${ampm}`;
}

// Allow pressing Enter to send message
document.getElementById('chatInput').addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        sendMessage();
    }
});


//Create Offer
document.getElementById('salary').addEventListener('input', function () {
    const salary = this.value;
    const salaryHelp = document.getElementById('salaryHelp');

    if (!/^\d+$/.test(salary) || parseInt(salary) <= 0) {
        salaryHelp.textContent = 'Salary must be a natural number greater than 0';
        this.setCustomValidity('Invalid');
    } else {
        salaryHelp.textContent = '';
        this.setCustomValidity('');
    }
});

document.getElementById("assignButton").addEventListener("click", function () {
    const path = window.location.pathname.split("/");
    const idSelect = path[path.length - 1];

    if (idSelect) {
        const select = document.getElementById("userSelect");
        for (const option of select.options) {
            if (option.value === idSelect) {
                option.selected = true;
                break;
            }
        }
    }
})

document.addEventListener("DOMContentLoaded", function () {
    const dueDateInput = document.getElementById("duedate");
    const fromDateInput = document.getElementById("from");
    const toDateInput = document.getElementById("to");

    function validateDates(event) {
        const today = new Date().toISOString().split("T")[0];
        const dueDate = dueDateInput.value;
        const fromDate = fromDateInput.value;
        const toDate = toDateInput.value;

        if (dueDate && dueDate < today) {
            alert("Due Date phải lớn hơn hoặc bằng ngày hiện tại.");
            event.preventDefault();
            return;
        }

        if (fromDate && toDate && fromDate > toDate) {
            alert("End Date (To) phải lớn hơn Start Date (From).");
            event.preventDefault();
        }
    }

    document.querySelector("form").addEventListener("submit", validateDates);
});

document.getElementById("interviewSelect").addEventListener("change", function () {
    let selectedOption = this.options[this.selectedIndex];
    // let note = selectedOption.getAttribute("data-note");
    document.getElementById("interviewNote").value = selectedOption.getAttribute("data-note");
});

/////////////////////////////////////////////////////