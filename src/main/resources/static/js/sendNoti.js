let stompClient = null;
let reminderButtons = {};

function initializeWebSocket() {
    console.log("Initializing WebSocket connection...");
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    // Disable debug logging
    stompClient.debug = null;

    stompClient.connect({}, function(frame) {
        console.log("Connected to WebSocket");
        stompClient.subscribe('/topic/messages', function(result) {
            handleNotification(JSON.parse(result.body));
        });
    }, function(error) {
        console.error("STOMP connection error:", error);
        // Try to reconnect after 5 seconds
        setTimeout(initializeWebSocket, 5000);
    });
}

function handleNotification(message) {
    console.log("Received notification:", message);

    // Handle regular WebSocket messages
    if (message.title && message.message) {
        // For reminder emails
        if (message.title.includes("Reminder") ||
            (message.message.content && message.message.content.includes("email"))) {

            showSuccessNotification("Email Sent Successfully",
                "Reminder email has been sent to the interviewer(s)");

            // Reset all reminder buttons that might have been in loading state
            resetReminderButtons();
        } else {
            // For other notifications
            showNotification(message.title,
                message.message.content || "You have a new notification");
        }
    }
}

function showNotification(title, message, type = 'info') {
    const container = document.getElementById('notificationContainer');
    if (!container) {
        console.error("Notification container not found!");
        return;
    }

    const toast = createToastElement(title, message, type);
    container.appendChild(toast);

    // Animate in
    setTimeout(() => toast.classList.add('show'), 10);

    // Auto dismiss after 5 seconds
    setTimeout(() => {
        toast.classList.remove('show');
        toast.classList.add('hiding');
        setTimeout(() => {
            if (container.contains(toast)) {
                container.removeChild(toast);
            }
        }, 300); // Match the CSS transition time
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

    // Get appropriate icon based on notification type
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

function setupReminderButtons() {
    console.log("Setting up reminder buttons");

    document.querySelectorAll('.reminder-btn').forEach(button => {
        const interviewId = button.getAttribute('data-interview-id');

        if (interviewId) {
            // Store reference to the button
            reminderButtons[interviewId] = button;

            button.addEventListener('click', function(e) {
                e.preventDefault();

                // Prevent multiple clicks
                if (this.classList.contains('btn-loading')) {
                    return;
                }

                // Show loading state on button
                this.classList.add('btn-loading');
                const originalContent = this.innerHTML;
                this.innerHTML = `
                    <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                    <span class="ms-1">Sending...</span>
                `;

                // Send AJAX request instead of navigating
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

                        // Show immediate success feedback since WebSocket might be delayed
                        showSuccessNotification("Email Sent Successfully",
                            "Reminder email has been sent to the interviewer(s)");

                        // Reset button state after short delay
                        setTimeout(() => {
                            this.classList.remove('btn-loading');
                            this.innerHTML = originalContent;
                        }, 1000);
                    })
                    .catch(error => {
                        console.error('Error sending reminder:', error);
                        showErrorNotification('Error', 'Failed to send reminder email. Please try again.');

                        // Reset button state
                        this.classList.remove('btn-loading');
                        this.innerHTML = originalContent;
                    });
            });
        }
    });
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

            if (span && span.textContent === 'Sending...') {
                span.remove();
            }
        }
    });
}

document.addEventListener("DOMContentLoaded", function() {
    console.log("DOM loaded - initializing notification system");
    initializeWebSocket();

    // Setup reminder buttons after a short delay to ensure DOM is fully processed
    setTimeout(setupReminderButtons, 100);
});