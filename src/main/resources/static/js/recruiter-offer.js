
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