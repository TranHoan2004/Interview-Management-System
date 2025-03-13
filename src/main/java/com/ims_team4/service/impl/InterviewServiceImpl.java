package com.ims_team4.service.impl;

import com.ims_team4.dto.InterviewDTO;
import com.ims_team4.model.Candidate;
import com.ims_team4.model.Interview;
import com.ims_team4.model.Job;
import com.ims_team4.model.utils.CandidateStatus;
import com.ims_team4.model.utils.InterviewStatus;
import com.ims_team4.repository.CandidateRepository;
import com.ims_team4.repository.InterviewRepository;
import com.ims_team4.repository.JobRepository;
import com.ims_team4.service.InterviewService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;


    @Autowired
    public InterviewServiceImpl(InterviewRepository interviewRepository,
                                CandidateRepository candidateRepository,
                                JobRepository jobRepository) {
        this.interviewRepository = interviewRepository;
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public List<InterviewDTO> getAllInterviews() {
        List<Interview> interviews = interviewRepository.findAll();
        return interviews.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * UC16: View/search interview schedules.
     */
    @Override
    public Page<InterviewDTO> searchInterviews(String search,
                                               InterviewStatus status,
                                               Long employeeId,
                                               int page,
                                               int size) {
        Pageable pageable = PageRequest.of(page, size);
//        Page<Interview> interviewPage =
//                interviewRepository.searchInterviews(search, status, employeeId, pageable);
//
//        List<InterviewDTO> dtoList = interviewPage.getContent()
//                .stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(dtoList, pageable, interviewPage.getTotalElements());
        return null;
    }

    /**
     * UC17: Create new interview schedule. - set InterviewStatus.NEW - candidate’s status -> WAITING_FOR_INTERVIEW
     */
    @Override
    public InterviewDTO createInterview(InterviewDTO interviewDTO) {
        Candidate candidate = candidateRepository.findById(interviewDTO.getCandidateId())
                .orElseThrow(() -> new RuntimeException("Candidate not found."));
        Job job = null;
        if (interviewDTO.getJobId() != null) {
            job = jobRepository.findById(interviewDTO.getJobId())
                    .orElseThrow(() -> new RuntimeException("Job not found."));
        }

        Interview interview = mapToEntity(interviewDTO);
        interview.setStatus(InterviewStatus.NEW);
        interview.setCandidate(candidate);
        interview.setJob(job);

        candidate.setStatus(CandidateStatus.WAITING_FOR_INTERVIEW);

        Interview saved = interviewRepository.save(interview);
        return mapToDTO(saved);
    }

    /**
     * UC18: View interview schedule details.
     */
    @Override
    public InterviewDTO getInterviewById(Long id) {
        Interview interview = interviewRepository.findInterviewWithCandidate(id)
                .orElseThrow(() -> new RuntimeException("Interview not found."));
        return mapToDTO(interview);
    }

    /**
     * UC20: Edit interview schedule details. - Disallow edit if CANCELLED or INTERVIEWED, etc.
     */
    @Override
    public InterviewDTO updateInterview(InterviewDTO interviewDTO) {
        Interview interview = interviewRepository.findById(interviewDTO.getId())
                .orElseThrow(() -> new RuntimeException("Interview not found."));

        if (interview.getStatus() == InterviewStatus.CANCELLED
            || interview.getStatus() == InterviewStatus.INTERVIEWED) {
            throw new IllegalStateException(
                    "Cannot edit a CANCELLED or INTERVIEWED interview.");
        }

        interview.setTitle(interviewDTO.getTitle());
        interview.setNote(interviewDTO.getNote());
        interview.setMeetId(interviewDTO.getMeetId());
        interview.setScheduleTime(interviewDTO.getScheduleTime());
        interview.setLocations(interviewDTO.getLocations());
        interview.setResult(interviewDTO.getResult());
        interview.setRecruiterOwner(interviewDTO.getRecruiterOwner());

        if (interviewDTO.getCandidateId() != 0) {
            Candidate candidate = candidateRepository.findById(interviewDTO.getCandidateId())
                    .orElseThrow(() -> new RuntimeException("Candidate not found."));
            interview.setCandidate(candidate);
        }

        if (interviewDTO.getJobId() != null) {
            Job job = jobRepository.findById(interviewDTO.getJobId())
                    .orElseThrow(() -> new RuntimeException("Job not found."));
            interview.setJob(job);
        }

        Interview updated = interviewRepository.save(interview);
        return mapToDTO(updated);
    }

    /**
     * UC19: Submit interview result (only the interviewer). - Once result is set, interview status -> INTERVIEWED -
     * Candidate status -> INTERVIEWED or FAILED
     */
    @Override
    public InterviewDTO submitInterviewResult(Long interviewId, String result) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found."));

        if (interview.getStatus() == InterviewStatus.CANCELLED
            || interview.getStatus() == InterviewStatus.INTERVIEWED) {
            throw new IllegalStateException("Interview is already CANCELLED or INTERVIEWED.");
        }

        interview.setResult(result);
        interview.setStatus(InterviewStatus.INTERVIEWED);

        Candidate candidate = interview.getCandidate();
        if (candidate != null) {
            if ("Passed".equalsIgnoreCase(result)) {
                candidate.setStatus(CandidateStatus.INTERVIEWED);
            } else if ("Failed".equalsIgnoreCase(result)) {
                candidate.setStatus(CandidateStatus.FAILED);
            }
        }

        Interview saved = interviewRepository.save(interview);
        return mapToDTO(saved);
    }

    /**
     * UC21: Cancel interview schedule - interview status -> CANCELLED - candidate status -> (Set to OPEN, or some
     * placeholder if you want CANCELLED)
     */
    @Override
    public InterviewDTO cancelInterview(Long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found."));

        if (interview.getStatus() == InterviewStatus.INTERVIEWED
            || interview.getStatus() == InterviewStatus.CANCELLED) {
            throw new IllegalStateException("Interview is already INTERVIEWED or CANCELLED.");
        }

        interview.setStatus(InterviewStatus.CANCELLED);

        Candidate candidate = interview.getCandidate();
        if (candidate != null) {
            candidate.setStatus(CandidateStatus.OPEN);
        }

        Interview saved = interviewRepository.save(interview);
        return mapToDTO(saved);
    }

    /**
     * UC22: Send reminder for upcoming schedule - Might set status -> INVITED - Possibly send an email
     */
    @Override
    public InterviewDTO sendReminder(Long interviewId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new RuntimeException("Interview not found."));

        if (interview.getStatus() == InterviewStatus.NEW) {
            interview.setStatus(InterviewStatus.INVITED);
            //emailService.sendReminder(interview);
        }

        Interview saved = interviewRepository.save(interview);
        return mapToDTO(saved);
    }

    // -----------------------------------
    // Helpers to map Interview <-> DTO
    // -----------------------------------
    private Interview mapToEntity(InterviewDTO dto) {
        if (dto == null) {
            return null;
        }
        return Interview.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .note(dto.getNote())
                .meetId(dto.getMeetId())
                .scheduleTime(dto.getScheduleTime())
                .status(dto.getStatus())
                .locations(dto.getLocations())
                .result(dto.getResult())
                .recruiterOwner(dto.getRecruiterOwner())
                .build();
    }

    private InterviewDTO mapToDTO(Interview interview) {
        if (interview == null) {
            return null;
        }
        InterviewDTO dto = new InterviewDTO();
        dto.setId(interview.getId());
        dto.setTitle(interview.getTitle());
        dto.setNote(interview.getNote());
        dto.setMeetId(interview.getMeetId());
        dto.setScheduleTime(interview.getScheduleTime());
        dto.setStatus(interview.getStatus());
        dto.setLocations(interview.getLocations());
        dto.setResult(interview.getResult());
        dto.setRecruiterOwner(interview.getRecruiterOwner());

        if (interview.getCandidate() != null) {
            dto.setCandidateId(interview.getCandidate().getId());
        }
        if (interview.getJob() != null) {
            dto.setJobId(interview.getJob().getId());
        }
        return dto;
    }
}
