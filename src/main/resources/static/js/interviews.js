document.addEventListener('DOMContentLoaded', function () {
    // Initialize Bootstrap dropdowns
    var dropdownElementList = [].slice.call(document.querySelectorAll('.dropdown-toggle'))
    var dropdownList = dropdownElementList.map(function (dropdownToggleEl) {
        return new bootstrap.Dropdown(dropdownToggleEl)
    });

    // Make user profile clickable for dropdown

    // Animate elements sequentially
    const animateElements = (selector, delay = 100) => {
        const elements = document.querySelectorAll(selector);
        elements.forEach((element, index) => {
            setTimeout(() => {
                element.classList.add('show');
            }, index * delay);
        });
    };

    // Stagger animations for different elements
    setTimeout(() => animateElements('.page-title'), 100);
    setTimeout(() => animateElements('.search-section'), 200);
    setTimeout(() => animateElements('.interview-card, tbody tr'), 300);
    setTimeout(() => animateElements('.pagination, .mt-4'), 500);

    // View toggle functionality
    const gridViewBtn = document.getElementById('gridViewBtn');
    const tableViewBtn = document.getElementById('tableViewBtn');
    const gridView = document.getElementById('gridView');
    const tableView = document.getElementById('tableView');

    if (gridViewBtn && tableViewBtn && gridView && tableView) {
        gridViewBtn.addEventListener('click', function () {
            gridView.style.display = 'grid';
            tableView.style.display = 'none';
            gridViewBtn.classList.add('active');
            tableViewBtn.classList.remove('active');
            // Re-trigger animations
            animateElements('.interview-card');
        });

        tableViewBtn.addEventListener('click', function () {
            gridView.style.display = 'none';
            tableView.style.display = 'block';
            gridViewBtn.classList.remove('active');
            tableViewBtn.classList.add('active');
            // Re-trigger animations
            animateElements('tbody tr');
        });
    }

    // Thêm lớp 'show' cho hàng tiêu đề của bảng
    document.querySelectorAll('.table thead tr').forEach((tr) => {
        tr.classList.add('show');
    });

    // Hide loading overlay
    setTimeout(() => {
        const loadingOverlay = document.getElementById('loadingOverlay');
        loadingOverlay.style.opacity = '0';
        setTimeout(() => {
            loadingOverlay.style.display = 'none';
        }, 300);
    }, 500);

    // Calculate stats for dashboard
    const calculateStats = () => {
        const interviews = document.querySelectorAll('[data-status]');
        let completed = 0;
        let upcoming = 0;
        let succeeded = 0;
        let total = interviews.length;

        interviews.forEach(interview => {
            const status = interview.getAttribute('data-status');
            const result = interview.getAttribute('data-result');

            if (status === 'INTERVIEWED') {
                completed++;
                if (result && result.toLowerCase().includes('pass')) {
                    succeeded++;
                }
            } else if (status === 'NEW' || status === 'INVITED') {
                upcoming++;
            }
        });

        const successRate = total > 0 ? Math.round((succeeded / total) * 100) : 0;

        document.getElementById('completedCount').textContent = completed;
        document.getElementById('upcomingCount').textContent = upcoming;
        document.getElementById('successRate').textContent = successRate + '%';
    };

    calculateStats();

    // Tab functionality
    const allTab = document.getElementById('allTab');
    const todayTab = document.getElementById('todayTab');
    const upcomingTab = document.getElementById('upcomingTab');
    const completedTab = document.getElementById('completedTab');

    const filterInterviews = (filterType) => {
        const interviews = document.querySelectorAll('.interview-card');
        const tableRows = document.querySelectorAll('tbody tr');

        const today = new Date().toISOString().split('T')[0];

        // Remove all 'show' classes first
        interviews.forEach(interview => {
            interview.classList.remove('show');
        });
        tableRows.forEach(row => {
            row.classList.remove('show');
        });

        // Apply the filter
        interviews.forEach(interview => {
            const status = interview.getAttribute('data-status');
            const scheduleDate = interview.querySelector('.interview-detail:nth-child(3) span').textContent.split(' ')[0];

            let show = false;

            if (filterType === 'all') {
                show = true;
            } else if (filterType === 'today') {
                // Convert date formats for comparison
                const dateParts = scheduleDate.split('/');
                const interviewDate = `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}`;
                show = interviewDate === today;
            } else if (filterType === 'upcoming') {
                show = status === 'NEW' || status === 'INVITED';
            } else if (filterType === 'completed') {
                show = status === 'INTERVIEWED';
            }

            if (show) {
                interview.style.display = '';
                setTimeout(() => {
                    interview.classList.add('show');
                }, 10);
            } else {
                interview.style.display = 'none';
            }
        });

        // Apply same filters to table rows
        tableRows.forEach(row => {
            const status = row.getAttribute('data-status');
            const scheduleDate = row.querySelector('td:nth-child(5) span').textContent.split(' ')[0];

            let show = false;

            if (filterType === 'all') {
                show = true;
            } else if (filterType === 'today') {
                // Convert date formats for comparison
                const dateParts = scheduleDate.split('/');
                const interviewDate = `${dateParts[2]}-${dateParts[1]}-${dateParts[0]}`;
                show = interviewDate === today;
            } else if (filterType === 'upcoming') {
                show = status === 'NEW' || status === 'INVITED';
            } else if (filterType === 'completed') {
                show = status === 'INTERVIEWED';
            }

            if (show) {
                row.style.display = '';
                setTimeout(() => {
                    row.classList.add('show');
                }, 10);
            } else {
                row.style.display = 'none';
            }
        });

        // Show no results message if needed
        const noResultsMessage = document.querySelector('.no-results');
        if (noResultsMessage) {
            const anyVisible = [...interviews].some(interview => interview.style.display !== 'none');
            noResultsMessage.style.display = anyVisible ? 'none' : 'block';
        }
    };

    // Set active tab
    const setActiveTab = (activeTab) => {
        [allTab, todayTab, upcomingTab, completedTab].forEach(tab => {
            tab.classList.remove('active');
        });
        activeTab.classList.add('active');
    };

    allTab.addEventListener('click', function () {
        setActiveTab(this);
        filterInterviews('all');
    });

    todayTab.addEventListener('click', function () {
        setActiveTab(this);
        filterInterviews('today');
    });

    upcomingTab.addEventListener('click', function () {
        setActiveTab(this);
        filterInterviews('upcoming');
    });

    completedTab.addEventListener('click', function () {
        setActiveTab(this);
        filterInterviews('completed');
    });

    // Show all interviews by default
    filterInterviews('all');

    // Add loading indicator to search form
    const searchForm = document.getElementById('searchForm');
    const searchButton = document.getElementById('searchButton');

    if (searchForm) {
        searchForm.addEventListener('submit', function () {
            searchButton.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Searching...';
            searchButton.disabled = true;

            // Re-enable after 3 seconds in case form doesn't submit
            setTimeout(() => {
                searchButton.innerHTML = '<i class="fa-solid fa-search me-1"></i> Search';
                searchButton.disabled = false;
            }, 3000);
        });
    }
});

document.addEventListener("DOMContentLoaded", function () {
    let sidebar = document.querySelector(".top-sidebar");
    sidebar.addEventListener("animationend", function () {
        sidebar.style.animation = "none";
    });
});

document.addEventListener("DOMContentLoaded", function () {
    // Set minimum date for schedule to today
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('scheduleTime').min = today;

    // Initialize Select2 for dropdowns
    $('.select2').select2({
        theme: 'bootstrap-5',
        width: '100%'
    });

    $('.select2-multiple').select2({
        theme: 'bootstrap-5',
        width: '100%',
        tags: false,
        tokenSeparators: [',', ' ']
    });

    // Character counter for notes
    const noteTextarea = document.getElementById('note');
    const charCount = document.getElementById('charCount');

    noteTextarea.addEventListener('input', function() {
        const currentLength = this.value.length;
        charCount.textContent = currentLength;

        if (currentLength >= 500) {
            charCount.style.color = '#dc3545';
        } else if (currentLength >= 400) {
            charCount.style.color = '#fd7e14';
        } else {
            charCount.style.color = '#6c757d';
        }
    });

    // Form validation
    const interviewForm = document.getElementById('interviewForm');

    interviewForm.addEventListener('submit', function(event) {
        event.preventDefault();

        // Validate time (from must be before to)
        const startTime = document.getElementById('startTime').value;
        const endTime = document.getElementById('endTime').value;

        if (startTime >= endTime) {
            showError('Schedule From Time must be earlier than Schedule To Time.');
            return;
        }

        // If all validations pass, submit the form
        this.submit();
    });

    // Load candidates via AJAX
    loadCandidates();

    // Load interviewers via AJAX
    loadInterviewers();

    // Load open jobs via AJAX
    loadOpenJobs();

    // Load recruiters via AJAX
    loadRecruiters();

    // Setup animation for top sidebar
    let sidebar = document.querySelector(".top-sidebar");
    if (sidebar) {
        sidebar.addEventListener("animationend", function () {
            sidebar.style.animation = "none";
        });
    }
});

// Helper functions
function showError(message) {
    const errorAlert = document.getElementById('errorAlert');
    const errorMessage = document.getElementById('errorMessage');

    errorMessage.textContent = message;
    errorAlert.classList.remove('d-none');

    // Scroll to the error message
    errorAlert.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

function showSuccess(message) {
    const successAlert = document.getElementById('successAlert');
    const successMessage = document.getElementById('successMessage');

    successMessage.textContent = message;
    successAlert.classList.remove('d-none');
}

let stompClient = null;
let reminderButtons = {};

// Khởi tạo kết nối WebSocket
function initializeWebSocket() {
    console.log("Initializing WebSocket connection...");
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    // Tắt debug logging
    stompClient.debug = null;

    stompClient.connect({}, function(frame) {
        console.log("Connected to WebSocket:", frame);
        stompClient.subscribe('/topic/messages', function(result) {
            handleNotification(JSON.parse(result.body));
        });
    }, function(error) {
        console.error("STOMP connection error:", error);
        // Thử kết nối lại sau 5 giây
        setTimeout(initializeWebSocket, 5000);
    });
}

// Xử lý message nhận được qua WebSocket
function handleNotification(message) {
    console.log("Received notification:", message);
    if (message.title && message.message) {
        // Nếu thông báo thuộc dạng reminder email
        if (message.title.includes("Reminder") ||
            (message.message.content && message.message.content.includes("email"))) {
            showSuccessNotification("Reminder Sent Successfully",
                "Reminder email has been sent to the interviewer(s)");
            resetReminderButtons();
        } else {
            showNotification(message.title,
                message.message.content || "You have a new notification");
        }
    }
}

// Hàm hiển thị thông báo dạng toast
function showNotification(title, message, type = 'info') {
    const container = document.getElementById('notificationContainer');
    if (!container) {
        console.error("Notification container not found!");
        return;
    }
    const toast = createToastElement(title, message, type);
    container.appendChild(toast);
    setTimeout(() => toast.classList.add('show'), 10);
    // Tự động ẩn sau 5 giây
    setTimeout(() => {
        toast.classList.remove('show');
        toast.classList.add('hiding');
        setTimeout(() => {
            if (container.contains(toast)) {
                container.removeChild(toast);
            }
        }, 300);
    }, 5000);
}

function showSuccessNotification(title, message) {
    showNotification(title, message, 'success');
}

function showErrorNotification(title, message) {
    showNotification(title, message, 'error');
}

function createToastElement(title, message, type) {
    const toast = document.createElement('div');
    toast.className = `toast-notification toast-${type}`;
    let icon = 'info-circle';
    if (type === 'success') icon = 'check-circle';
    if (type === 'error') icon = 'exclamation-circle';
    if (type === 'warning') icon = 'exclamation-triangle';
    toast.innerHTML = `
        <div class="toast-header">
            <i class="fas fa-${icon} me-2"></i>
            <strong>${title}</strong>
            <button type="button" class="btn-close ms-auto" onclick="this.parentElement.parentElement.remove()"></button>
        </div>
        <div class="toast-body">
            ${message}
        </div>
    `;
    return toast;
}

// Hàm thiết lập các nút reminder
function setupReminderButtons() {
    console.log("Setting up reminder buttons");
    document.querySelectorAll('.reminder-btn').forEach(button => {
        const interviewId = button.getAttribute('data-interview-id');
        if (interviewId) {
            reminderButtons[interviewId] = button;
            button.addEventListener('click', function(e) {
                e.preventDefault();
                if (this.classList.contains('btn-loading')) {
                    return;
                }
                this.classList.add('btn-loading');
                const originalContent = this.innerHTML;
                this.innerHTML = `
                    <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                    <span class="ms-1">Sending...</span>
                `;
                // Gọi AJAX đến endpoint /reminder
                fetch(this.href, {
                    method: 'GET',
                    headers: {
                        'X-Requested-With': 'XMLHttpRequest'
                    }
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(data => {
                        // Hiển thị thông báo thành công
                        showSuccessNotification("Email Sent Successfully", data.message);
                        // Gửi thông báo qua WebSocket sử dụng dữ liệu trả về
                        sendWebSocketNotification(data.notification);
                        // Reset trạng thái nút
                        setTimeout(() => {
                            this.classList.remove('btn-loading');
                            this.innerHTML = originalContent;
                        }, 1000);
                    })
                    .catch(error => {
                        console.error('Error sending reminder:', error);
                        showErrorNotification('Error', 'Failed to send reminder email. Please try again.');
                        this.classList.remove('btn-loading');
                        this.innerHTML = originalContent;
                    });
            });
        }
    });
}

// Hàm gửi thông báo qua WebSocket
function sendWebSocketNotification(notification) {
    const messagePayload = {
        message: {
            from: 'interview',
            content: {
                link: notification.link,
                message: notification.message
            },
            to: notification.userID  // user nhận thông báo
        },
        title: notification.title
    };
    stompClient.send("/app/application", {}, JSON.stringify(messagePayload));
}

function resetReminderButtons() {
    Object.values(reminderButtons).forEach(button => {
        if (button.classList.contains('btn-loading')) {
            button.classList.remove('btn-loading');
            const icon = button.querySelector('i');
            const span = button.querySelector('span:not(.tooltip-text)');
            if (icon && icon.classList.contains('spinner-border')) {
                icon.className = 'fa fa-bell';
            }
            if (span && span.textContent.trim() === 'Sending...') {
                span.remove();
            }
        }
    });
}

document.addEventListener("DOMContentLoaded", function() {
    console.log("DOM loaded - initializing notification system");
    initializeWebSocket();
    setTimeout(setupReminderButtons, 100);
});


