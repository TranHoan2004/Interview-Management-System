    document.addEventListener("DOMContentLoaded", function () {
    // Set minimum date for schedule to today
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('scheduleTime').min = today;

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

});

    document.addEventListener("DOMContentLoaded", function () {
        // ...existing code...

        // Form validation
        const interviewForm = document.getElementById('interviewForm');

        interviewForm.addEventListener('submit', function(event) {
            event.preventDefault();

            // Clear previous errors
            clearAllErrors();
            let hasErrors = false;

            // Validate title (required, 5-100 chars)
            const title = document.getElementById('title').value.trim();
            if (!title) {
                showFieldError('title', 'Interview title is required');
                hasErrors = true;
            } else if (title.length < 5) {
                showFieldError('title', 'Title must be at least 5 characters');
                hasErrors = true;
            } else if (title.length > 100) {
                showFieldError('title', 'Title cannot exceed 100 characters');
                hasErrors = true;
            }

            // Validate candidateId (required, positive integer)
            const candidateId = document.getElementById('candidateId').value;
            if (!candidateId) {
                showFieldError('candidateId', 'Candidate ID is required');
                hasErrors = true;
            } else if (!Number.isInteger(Number(candidateId)) || Number(candidateId) <= 0) {
                showFieldError('candidateId', 'Candidate ID must be a positive number');
                hasErrors = true;
            }

            // Validate jobId (required, positive integer)
            const jobId = document.getElementById('jobId').value;
            if (!jobId) {
                showFieldError('jobId', 'Job ID is required');
                hasErrors = true;
            } else if (!Number.isInteger(Number(jobId)) || Number(jobId) <= 0) {
                showFieldError('jobId', 'Job ID must be a positive number');
                hasErrors = true;
            }

            // Validate schedule date (required, not in the past)
            const scheduleTime = document.getElementById('scheduleTime').value;
            if (!scheduleTime) {
                showFieldError('scheduleTime', 'Schedule date is required');
                hasErrors = true;
            }

            // Validate start and end times
            const startTime = document.getElementById('startTime').value;
            const endTime = document.getElementById('endTime').value;

            if (!startTime) {
                showFieldError('startTime', 'Start time is required');
                hasErrors = true;
            }

            if (!endTime) {
                showFieldError('endTime', 'End time is required');
                hasErrors = true;
            }

            if (startTime && endTime && startTime >= endTime) {
                showFieldError('endTime', 'End time must be after start time');
                hasErrors = true;
            }

            // Validate location (required, 3-100 chars)
            const location = document.getElementById('locations').value.trim();
            if (!location) {
                showFieldError('locations', 'Location is required');
                hasErrors = true;
            } else if (location.length < 3) {
                showFieldError('locations', 'Location must be at least 3 characters');
                hasErrors = true;
            } else if (location.length > 100) {
                showFieldError('locations', 'Location cannot exceed 100 characters');
                hasErrors = true;
            }

            // Validate meetId (optional, but if provided, should be valid URL or meeting ID)
            const meetId = document.getElementById('meetId').value.trim();
            if (meetId) {
                // Basic URL validation or meeting ID format check
                const urlPattern = /^(https?:\/\/)?([\w-]+(\.[\w-]+)+|localhost)(:\d+)?(\/\S*)?$/;
                const meetingIdPattern = /^\d{9,11}$|^[\w\d]{10,15}$/; // Common Zoom/Google Meet ID formats

                if (!urlPattern.test(meetId) && !meetingIdPattern.test(meetId)) {
                    showFieldError('meetId', 'Please enter a valid URL or meeting ID');
                    hasErrors = true;
                }
            }

            // Validate recruiterOwner (required, positive integer)
            const recruiterOwner = document.getElementById('recruiterOwner').value;
            if (!recruiterOwner) {
                showFieldError('recruiterOwner', 'Recruiter ID is required');
                hasErrors = true;
            } else if (!Number.isInteger(Number(recruiterOwner)) || Number(recruiterOwner) <= 0) {
                showFieldError('recruiterOwner', 'Recruiter ID must be a positive number');
                hasErrors = true;
            }

            // If any errors were found, prevent form submission
            if (hasErrors) {
                // Show general error message
                showError('Please fix the errors highlighted below.');
                return;
            }

            // If all validations pass, submit the form
            showSuccess('Validating your form...');
            this.submit();
        });

        // Helper functions
        function showFieldError(fieldId, message) {
            const field = document.getElementById(fieldId);
            const parentDiv = field.closest('.mb-3');

            // Add error class to input
            field.classList.add('is-invalid');

            // Create error message element if it doesn't exist
            let errorDiv = parentDiv.querySelector('.invalid-feedback');
            if (!errorDiv) {
                errorDiv = document.createElement('div');
                errorDiv.className = 'invalid-feedback';
                parentDiv.appendChild(errorDiv);
            }

            errorDiv.textContent = message;
        }

        function clearAllErrors() {
            // Remove all error messages and styling
            document.querySelectorAll('.is-invalid').forEach(el => {
                el.classList.remove('is-invalid');
            });

            document.querySelectorAll('.invalid-feedback').forEach(el => {
                el.remove();
            });

            // Hide the error alert
            document.getElementById('errorAlert').classList.add('d-none');
            document.getElementById('successAlert').classList.add('d-none');
        }

        // Add real-time validation for important fields
        document.getElementById('title').addEventListener('blur', function() {
            if (this.value.trim() && this.value.trim().length < 5) {
                showFieldError('title', 'Title must be at least 5 characters');
            } else {
                this.classList.remove('is-invalid');
                const errorDiv = this.closest('.mb-3').querySelector('.invalid-feedback');
                if (errorDiv) errorDiv.remove();
            }
        });

        // Add similar real-time validation for other important fields
        const timeInputs = document.querySelectorAll('input[type="time"]');
        timeInputs.forEach(input => {
            input.addEventListener('change', function() {
                validateTimes();
            });
        });

        function validateTimes() {
            const startTime = document.getElementById('startTime').value;
            const endTime = document.getElementById('endTime').value;

            if (startTime && endTime && startTime >= endTime) {
                showFieldError('endTime', 'End time must be after start time');
            } else {
                document.getElementById('endTime').classList.remove('is-invalid');
                const errorDiv = document.getElementById('endTime').closest('.mb-3').querySelector('.invalid-feedback');
                if (errorDiv) errorDiv.remove();
            }
        }

        // Function to show error message
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
    });