package com.ims_team4.service;

import com.ims_team4.dto.InterviewDTO;

import java.util.List;

// Tran Dang Vu
public interface InterviewService {
    InterviewDTO createInterview(InterviewDTO interviewDTO);

    InterviewDTO updateInterview(Long id, InterviewDTO interviewDTO);

    InterviewDTO getInterviewById(Long id);

    List<InterviewDTO> getAllInterviews();

    void cancelInterview(Long id);

    InterviewDTO submitInterviewResult(Long id, String result, String feedback);

    void sendReminderForUpcoming();

}