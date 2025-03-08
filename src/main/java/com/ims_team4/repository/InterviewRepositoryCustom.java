package com.ims_team4.repository;

import com.ims_team4.model.Interview;
import java.time.LocalDateTime;
import java.util.List;
//Tran Dang Vu
public interface InterviewRepositoryCustom {
    List<Interview> findUpcomingInterviews(LocalDateTime from, LocalDateTime to);
    List<Interview> searchInterviews(String keyword,
                                     String interviewerName,
                                     String status);
}
