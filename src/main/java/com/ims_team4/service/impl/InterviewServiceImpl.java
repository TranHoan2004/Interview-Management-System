package com.ims_team4.service.impl;

import com.ims_team4.dto.InterviewDTO;
import com.ims_team4.dto.OfferDTO;
import com.ims_team4.model.Interview;
import com.ims_team4.model.Offer;
import com.ims_team4.model.utils.InterviewStatus;
import com.ims_team4.repository.InterviewRepository;
import com.ims_team4.service.InterviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
// Tran Dang Vu
public class InterviewServiceImpl implements InterviewService {
    private final InterviewRepository interviewRepository;

    @Autowired
    public InterviewServiceImpl(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    public InterviewDTO createInterview(InterviewDTO dto) {
        return null;
    }

    @Override
    public InterviewDTO updateInterview(InterviewDTO dto) {
        return null;
    }

    @Override
    public InterviewDTO getInterviewById(Long id) {
        return null;
    }

    @Override
    public List<InterviewDTO> getAllInterviews() {
        return interviewRepository.getAllInterview().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InterviewDTO submitInterviewResult(Long interviewId, String result) {
        return null;
    }

    @Override
    public void cancelInterview(Long interviewId) {

    }

    @Override
    public void sendReminder(Long interviewId) {

    }

    @Override
    public List<InterviewDTO> findUpcomingInterviews(LocalDateTime from, LocalDateTime to) {
        return List.of();
    }

    @Override
    public List<InterviewDTO> searchInterviews(String keyword, String interviewerName, String status) {
        return List.of();
    }

    private InterviewDTO convertToDTO(Interview interview) {
        return InterviewDTO.builder()
                .id(interview.getId())
                .jobId(interview.getJob().getId())
                .title(interview.getTitle())
                .note(interview.getNote())
                .meetId(interview.getMeetId())
                .scheduleTime(interview.getScheduleTime())
                .status(interview.getStatus())
                .locations(interview.getLocations())
                .result(interview.getResult())
                .candidateId(interview.getCandidate().getId())
                .recruiterOwner(interview.getRecruiterOwner())
                .build();
    }

//    /**
//    *Tạo mới lịch phỏng vấn (UC17)
//    */
//    @Override
//    public InterviewDTO createInterview(InterviewDTO dto) {
//        Interview interview = dtoToEntity(dto);
//
//        interview.setStatus(InterviewStatus.NEW);
//        interview.setScheduleTime(dto.getScheduleTime()); // Lấy từ DTO
//        Interview saved = interviewRepository.save(interview);
//        // Convert ngược -> DTO
//        return entityToDto(saved);
//    }
//
//    /**
//    // Sửa thông tin lịch phỏng vấn (UC20)
//    */
//    @Override
//    public InterviewDTO updateInterview(InterviewDTO dto) {
//        Optional<Interview> optionalInterview = interviewRepository.findById(dto.getId());
//        if (optionalInterview.isEmpty()) {
//            throw new EntityNotFoundException("Interview not found with ID = " + dto.getId());
//        }
//
//        Interview interview = optionalInterview.get();
//        if (interview.getStatus() == InterviewStatus.CANCELLED
//                || interview.getStatus() == InterviewStatus.INTERVIEWED) {
//            throw new IllegalStateException("Cannot update interview in " + interview.getStatus() + " status.");
//        }
//
//        interview.setFeedback(dto.getFeedback());
//        interview.setScheduleTime(dto.getScheduleTime());
//        interview.setLocations(dto.getLocations());
//        interview.setResult(dto.getResult());
//        Interview updated = interviewRepository.save(interview);
//        return entityToDto(updated);
//    }
//
//    // ---------------------------------
//    // Xem chi tiết interview
//    // ---------------------------------
//    @Override
//    public InterviewDTO getInterviewById(Long id) {
//        Interview interview = interviewRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Interview not found with ID = " + id));
//        return entityToDto(interview);
//    }
//
//    @Override
//    public List<InterviewDTO> getAllInterviews() {
//        List<InterviewDTO> result = new ArrayList<>();
//        Iterable<Interview> iterable = interviewRepository.findAll();
//        iterable.forEach(i -> result.add(entityToDto(i)));
//        return result;
//    }
//
//    // Submit kết quả
//    @Override
//    public InterviewDTO submitInterviewResult(Long interviewId, String result) {
//        Interview interview = interviewRepository.findById(interviewId)
//                .orElseThrow(() -> new EntityNotFoundException("Interview not found with ID = " + interviewId));
//        if (interview.getStatus() == InterviewStatus.CANCELLED) {
//            throw new IllegalStateException("Cannot submit result to a cancelled interview");
//        }
//
//        interview.setResult(result);
//
//        interview.setStatus(InterviewStatus.INTERVIEWED);
//
//        Interview saved = interviewRepository.save(interview);
//
//        return entityToDto(saved);
//    }
//
//    // Huỷ phỏng vấn
//    @Override
//    public void cancelInterview(Long interviewId) {
//        Interview interview = interviewRepository.findById(interviewId)
//                .orElseThrow(() -> new EntityNotFoundException("Interview not found with ID = " + interviewId));
//
//        if (interview.getStatus() == InterviewStatus.INTERVIEWED) {
//
//            throw new IllegalStateException("Cannot cancel an interview that is already interviewed.");
//        }
//
//        interview.setStatus(InterviewStatus.CANCELLED);
//        interviewRepository.save(interview);
//
//
//    }
//
//    // Gửi reminder
//    @Override
//    public void sendReminder(Long interviewId) {
//        Interview interview = interviewRepository.findById(interviewId)
//                .orElseThrow(() -> new EntityNotFoundException("Interview not found with ID = " + interviewId));
//
//        // 1. Gửi email/notification cho interviewer (tuỳ logic)
//        //    Code mẫu, tuỳ bạn implement EmailService:
//        // emailService.sendReminder(interview);
//
//        // 2. Set status -> INVITED (nếu hiện tại vẫn đang là NEW)
//        if (InterviewStatus.NEW.equals(interview.getStatus())) {
//            interview.setStatus(InterviewStatus.INVITED);
//            interviewRepository.save(interview);
//        }
//    }
//
//    // Tìm interview sắp tới
//    @Override
//    public List<InterviewDTO> findUpcomingInterviews(LocalDateTime from, LocalDateTime to) {
//        return interviewRepository.findUpcomingInterviews(from, to)
//                .stream()
//                .map(this::entityToDto)
//                .toList();
//    }
//
//    // Tìm interview với nhiều tiêu chí (keyword, v.v.)
//    @Override
//    public List<InterviewDTO> searchInterviews(String keyword, String interviewerName, String status) {
//        return interviewRepository.searchInterviews(keyword, interviewerName, status)
//                .stream()
//                .map(this::entityToDto)
//                .toList();
//    }
//
//    private Interview dtoToEntity(InterviewDTO dto) {
//        Interview interview = new Interview();
//        interview.setId(dto.getId());
//        interview.setFeedback(dto.getFeedback());
//        interview.setLocations(dto.getLocations());
//        interview.setResult(dto.getResult());
//        interview.setScheduleTime(dto.getScheduleTime());
//
//        if (dto.getStatus() != null) {
//            try {
//                interview.setStatus(InterviewStatus.valueOf(dto.getStatus().toUpperCase()));
//            } catch (IllegalArgumentException e) {
//                interview.setStatus(InterviewStatus.NEW);
//            }
//        } else {
//            interview.setStatus(InterviewStatus.NEW);
//        }
//
//        return interview;
//    }
//
//    private InterviewDTO entityToDto(Interview interview) {
//        InterviewDTO dto = new InterviewDTO();
//        dto.setId(interview.getId() == null ? 0 : interview.getId());
//        dto.setFeedback(interview.getFeedback());
//        dto.setLocations(interview.getLocations());
//        dto.setResult(interview.getResult());
//        dto.setScheduleTime(interview.getScheduleTime());
//
//        if (interview.getStatus() != null) {
//            dto.setStatus(interview.getStatus().name());
//        }
//
//        if (interview.getJob() != null) {
//            dto.setJobId(interview.getJob().getId());
//        } else {
//            dto.setJobId(0);
//        }
//
//        return dto;
//    }
}
