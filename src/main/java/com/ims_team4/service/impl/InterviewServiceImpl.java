package com.ims_team4.service.impl;

import com.ims_team4.dto.InterviewDTO;
import com.ims_team4.model.Employee;
import com.ims_team4.model.Interview;
import com.ims_team4.model.utils.InterviewStatus;
import com.ims_team4.repository.InterviewRepository;
import com.ims_team4.service.InterviewService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Example InterviewServiceImpl showing how to handle typical UC16–UC22 flows using the CandidateStatus enum.
 * <p>
 * Adjust exception handling, logging, or candidate/job status updates as needed.
 */
@Service
@Transactional
public class InterviewServiceImpl implements InterviewService {
    private final InterviewRepository interviewRepository;

    @Autowired
    public InterviewServiceImpl(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    // ---------------------------------------------------------------------
    // UC16 - Simple listing
    @Override
    public List<InterviewDTO> getAllInterviews() {
        List<Interview> interviews = interviewRepository.findAll();
        return interviews.stream()
                .map(this::mapEntity)
                .collect(Collectors.toList());
    }

    // ---------------------------------------------------------------------
    // UC16 - Searching & paging
    @Override
    public Page<InterviewDTO> searchInterviews(String search,
                                               InterviewStatus status,
                                               Long employeeId,
                                               int page,
                                               int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("scheduleTime").descending());
        Page<Interview> interviewPage = interviewRepository.searchInterviews(search, status, employeeId, pageable);

        List<InterviewDTO> dtoList = interviewPage.getContent()
                .stream()
                .map(this::mapEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, interviewPage.getTotalElements());
    }

    // ---------------------------------------------------------------------
    // UC17 - Create a new interview schedule
    @Override
    public InterviewDTO createInterview(Interview interview) {
        interview.setStatus(InterviewStatus.NEW);
        Interview saved = interviewRepository.save(interview);
        return mapEntity(saved);
    }

    // ---------------------------------------------------------------------
    // UC18 - View interview schedule details
    @Override
    public InterviewDTO getInterviewById(Long interviewId) {
        Interview interview = interviewRepository.findInterviewDetails(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found with ID=" + interviewId));
        return mapEntity(interview);
    }

    @Override
    public Interview findEntityById(Long interviewId) {
        return interviewRepository
                .findInterviewDetails(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found with ID=" + interviewId));
    }

    // ---------------------------------------------------------------------
    // UC20 - Edit interview schedule details
    @Override
    public InterviewDTO updateInterview(Interview interview) {
        Interview old = interviewRepository.findById(interview.getId())
                .orElseThrow(() -> new RuntimeException("Interview not found."));

        if (old.getStatus() == InterviewStatus.CANCELLED
            || old.getStatus() == InterviewStatus.INTERVIEWED) {
            throw new IllegalStateException("Cannot edit a CANCELLED or INTERVIEWED interview.");
        }

        interview.setStatus(old.getStatus());

        Interview updated = interviewRepository.save(interview);
        return mapEntity(updated);
    }

    // ---------------------------------------------------------------------
    // UC19 - Submit interview result
    @Override
    public InterviewDTO submitInterviewResult(Long interviewId, String result) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found with ID=" + interviewId));

        if (interview.getStatus() == InterviewStatus.CANCELLED
            || interview.getStatus() == InterviewStatus.INTERVIEWED) {
            throw new IllegalStateException("Interview is already CANCELLED or INTERVIEWED.");
        }

        interview.setResult(result);
        interview.setStatus(InterviewStatus.INTERVIEWED);

        Interview saved = interviewRepository.save(interview);
        return mapEntity(saved);
    }

    // ---------------------------------------------------------------------
    // UC21 - Cancel interview schedule
    @Override
    public InterviewDTO cancelInterview(Long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found with ID=" + interviewId));

        if (interview.getStatus() == InterviewStatus.INTERVIEWED
            || interview.getStatus() == InterviewStatus.CANCELLED) {
            throw new IllegalStateException("Interview is already INTERVIEWED or CANCELLED.");
        }

        interview.setStatus(InterviewStatus.CANCELLED);

        Interview saved = interviewRepository.save(interview);
        return mapEntity(saved);
    }

    // ---------------------------------------------------------------------
    // UC22 - Send reminder
    @Override
    public InterviewDTO sendReminder(Long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found with ID=" + interviewId));

        if (interview.getStatus() == InterviewStatus.NEW) {
            interview.setStatus(InterviewStatus.INVITED);
            //emailService.sendReminder(interview);
        }

        Interview saved = interviewRepository.save(interview);
        return mapEntity(saved);
    }

    // HoanTX
    @Override
    public List<InterviewDTO> getUpcomingInterviewDTOs(String schedule, LocalTime startTime) {
        List<Interview> interviews = interviewRepository.findInterviewsByStartTimeAndScheduleTime(Date.valueOf(schedule), startTime);
        return interviews.stream().map(this::mapEntity).collect(Collectors.toList());
    }

    @Override
    public void updateNotificationSent(Long id) {
        interviewRepository.updateNotificationSent(id);
    }

    // <editor-fold> desc="Helpers to map Interview <-> DTO"
    private InterviewDTO mapEntity(@NotNull Interview interview) {
        if (interview == null) return null;

        // Convert employees -> set of IDs
        Set<Long> employeeIds = null;
        if (interview.getEmployees() != null) {
            employeeIds = interview.getEmployees().stream()
                    .map(Employee::getId)
                    .collect(Collectors.toSet());
        }

        return InterviewDTO.builder()
                .id(interview.getId())
                .title(interview.getTitle())
                .note(interview.getNote())
                .meetId(interview.getMeetId())
                .scheduleTime(interview.getScheduleTime())
                .startTime(interview.getStartTime())
                .endTime(interview.getEndTime())
                .status(interview.getStatus())
                .locations(interview.getLocations())
                .result(interview.getResult())
                .candidateId(interview.getCandidate() != null
                        ? interview.getCandidate().getId()
                        : 0)
                .jobId(interview.getJob() != null
                        ? interview.getJob().getId()
                        : null)
                .employeeIds(employeeIds)
                .build();
    }

    //VuTD
//    private InterviewDTO mapToDTO(Interview interview) {
//        if (interview == null) return null;
//
//        // Convert employees -> set of IDs
//        Set<Long> employeeIds = null;
//        if (interview.getEmployees() != null) {
//            employeeIds = interview.getEmployees().stream()
//                    .map(Employee::getId)
//                    .collect(Collectors.toSet());
//        }
//
//        return InterviewDTO.builder()
//                .id(interview.getId())
//                .title(interview.getTitle())
//                .note(interview.getNote())
//                .meetId(interview.getMeetId())
//                .scheduleTime(interview.getScheduleTime())
//                .startTime(interview.getStartTime())
//                .endTime(interview.getEndTime())
//                .status(interview.getStatus())
//                .locations(interview.getLocations())
//                .result(interview.getResult())
//                .candidateId(interview.getCandidate() != null
//                        ? interview.getCandidate().getId()
//                        : 0)
//                .jobId(interview.getJob() != null
//                        ? interview.getJob().getId()
//                        : null)
//                .employeeIds(employeeIds)
//                .build();
//    }
    // </editor-fold>
}
