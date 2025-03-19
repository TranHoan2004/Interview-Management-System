package com.ims_team4.model.utils;

public enum CandidateStatus {
    OPEN,                    // Khi ứng viên được tạo và chưa được xếp lịch phỏng vấn
    WAITING_FOR_INTERVIEW,    // Khi ứng viên đã có lịch phỏng vấn
    CANCELLED_INTERVIEW,      // Khi phỏng vấn bị hủy
    PASSED_INTERVIEW,         // Khi ứng viên đậu phỏng vấn
    FAILED_INTERVIEW,         // Khi ứng viên rớt phỏng vấn
    WAITING_FOR_APPROVAL,     // Khi offer được tạo và đang chờ Manager duyệt
    APPROVED_OFFER,           // Khi Manager chấp nhận offer
    REJECTED_OFFER,           // Khi Manager từ chối offer
    WAITING_FOR_RESPONSE,     // Khi offer được gửi cho ứng viên và chờ phản hồi
    ACCEPTED_OFFER,           // Khi ứng viên chấp nhận offer
    DECLINED_OFFER,           // Khi ứng viên từ chối offer
    CANCELLED_OFFER,          // Khi Recruiter hủy offer
    BANNED                    // Khi ứng viên bị cấm bởi Recruiter
}
