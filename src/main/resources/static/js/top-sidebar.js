console.log('lang: ', lang)

window.addEventListener("DOMContentLoaded", function loadNotification() {
    $.ajax({
        url: "/notification/list",
        type: "post",
        data: {},
        success: function (responseData) {
            let render = document.getElementById("divider-notification");
            let count = 0;
            responseData.forEach(function (noti) {
                // #e6f7ff haven't read yet
                // #ffffff have read
                const notificationData = data(noti, noti.status ? '#ffffff' : '#e6f7ff');
                render.insertAdjacentHTML('beforeend', notificationData);
                if (!noti.status) {
                    count++;
                }
            });
            document.getElementById("notificationSize").innerHTML = responseData.length;
            document.getElementById("notificationTitle").innerHTML = underTitle(count);
        }
    });
}, false);

const data = (noti, style) => {
    const iconHtml = notificationIcon(noti.title);
    return `
        <a class="dropdown-item notification-item unread" style="background-color: ${style}" onclick="redirect(${noti.id})">
            <div class="notification-icon bg-success">${iconHtml}</div>
            <div class="notification-text ellipsis">
                  <p class="mb-0">${noti.title}</p>
                  <span class="text-muted small">${noti.message}</span>
            </div>
        </a>`;
}

function redirect(id) {
    console.log(id)
    fetch('http://localhost:8080/notification/update/' + id, {
        method: 'POST'
    }).then(r => r.text()
    ).then(link => {
        window.location.href = link;
    });
}

// Hàm chọn icon dựa trên tiêu đề thông báo
const notificationIcon = (title) => {
    if (title.toLowerCase().includes('interview')) {
        return "<i class='fas fa-user-check text-white'></i>";
    } else if (title.toLowerCase().includes('user')) {
        return "<i class='fas fa-user text-white'></i>";
    } else if (title.toLowerCase().includes('job')) {
        return "<i class='fas fa-briefcase text-white'></i>";
    } else if (title.toLowerCase().includes('offer')) {
        return "<i class='fas fa-handshake text-white'></i>";
    } else if (title.toLowerCase().includes('candidate')) {
        return "<i class='fas fa-user-tie text-white'></i>";
    }
    return "<i class='fas fa-calendar text-white'></i>";
}

const underTitle = (count) => {
    let noti = "";
    switch (lang) {
        case 'vi':
            noti = "Bạn có " + count + " tin nhắn chưa đọc";
            break;
        case 'jp':
            noti = count + " 件の未読メッセージがあります";
            break;
        case 'en':
        case 'en_US':
            noti = "You have " + count + " unread messages";
            break;
    }
    console.log(noti)
    return noti;
}