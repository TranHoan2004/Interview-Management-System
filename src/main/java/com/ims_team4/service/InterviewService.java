package com.ims_team4.service;

import com.ims_team4.dto.InterviewDTO;

import java.time.LocalDateTime;
import java.util.List;
//Tran Dang Vu
public interface InterviewService {

    InterviewDTO createInterview(InterviewDTO dto);

    InterviewDTO updateInterview(InterviewDTO dto);

    InterviewDTO getInterviewById(Long id);
    List<InterviewDTO> getAllInterviews();

    InterviewDTO submitInterviewResult(Long interviewId, String result);

    void cancelInterview(Long interviewId);

    void sendReminder(Long interviewId);

    List<InterviewDTO> findUpcomingInterviews(LocalDateTime from, LocalDateTime to);

    List<InterviewDTO> searchInterviews(String keyword, String interviewerName, String status);
}
