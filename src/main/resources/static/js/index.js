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

    // Logout function
    window.logout = function () {
        if (confirm('Are you sure you want to logout?')) {
            window.location.href = '/logout';
        }
    }
});

document.addEventListener("DOMContentLoaded", function () {
    let sidebar = document.querySelector(".top-sidebar");
    sidebar.addEventListener("animationend", function () {
        sidebar.style.animation = "none";
    });
});