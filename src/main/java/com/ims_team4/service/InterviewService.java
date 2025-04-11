package com.ims_team4.service;

import com.ims_team4.dto.InterviewDTO;
import com.ims_team4.model.Interview;
import com.ims_team4.model.utils.InterviewStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface InterviewService {
    // UC16: View/search interview schedules
    List<InterviewDTO> getAllInterviews();


    Page<InterviewDTO> searchInterviews(String search,
                                        InterviewStatus status,
                                        Long employeeId,
                                        int page,
                                        int size);

    // UC17: Create new interview schedule
    InterviewDTO createInterview(Interview interview);

    // UC18: View interview schedule details
    InterviewDTO getInterviewById(Long interviewId);

    // For reading the underlying entity
    Interview findEntityById(Long interviewId);

    // UC20: Edit interview details
    InterviewDTO updateInterview(Interview interview);

    // UC19: Submit interview result
    InterviewDTO submitInterviewResult(Long interviewId, String result);

    // UC21: Cancel interview schedule
    InterviewDTO cancelInterview(Long interviewId);

    // <editor-fold> desc="UC22: Send reminder for upcoming schedule"
    InterviewDTO sendReminder(Long interviewId);

    // HoanTX
    List<InterviewDTO> getUpcomingInterviewDTOs(String schedule, String startTime);

    void updateNotificationSent(Long id);
    // </editor-fold>
}
