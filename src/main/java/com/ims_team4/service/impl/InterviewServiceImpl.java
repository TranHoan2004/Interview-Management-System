package com.ims_team4.service.impl;

import com.ims_team4.dto.InterviewDTO;
import com.ims_team4.model.Interview;
import com.ims_team4.repository.InterviewRepository;
import com.ims_team4.repository.impl.InterviewRepositoryImpl;
import com.ims_team4.service.InterviewService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
// Tran Dang Vu
public class InterviewServiceImpl implements InterviewService {
    private final InterviewRepository interviewRepository;

    public InterviewServiceImpl(InterviewRepositoryImpl interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    public InterviewDTO createInterview(InterviewDTO interviewDTO) {
        Interview interview = convertToEntity(interviewDTO);
        Interview saved = interviewRepository.save(interview);
        return convertToDTO(saved);
    }

    @Override
    public InterviewDTO updateInterview(Long id, InterviewDTO interviewDTO) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));
        interview.setFeedback(interviewDTO.getFeedback());
        interview.setScheduleTime(interviewDTO.getScheduleTime().toLocalDate());
        interview.setStatus(interviewDTO.isStatus());
        interview.setLocations(interviewDTO.getLocations());
        interview.setResult(interviewDTO.getResult());

        Interview updated = interviewRepository.save(interview);
        return  convertToDTO(updated);
    }

    @Override
    public InterviewDTO getInterviewById(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));
        return convertToDTO(interview);
    }

    @Override
    public List<InterviewDTO> getAllInterviews() {
        return interviewRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelInterview(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));
        interview.setStatus(false);
        interviewRepository.save(interview);
    }

    @Override
    public InterviewDTO submitInterviewResult(Long id, String result, String feedback) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found with id: " + id));
        interview.setResult(result);
        interview.setFeedback(feedback);
        Interview updated = interviewRepository.save(interview);
        return convertToDTO(updated);
    }

    @Override
    public void sendReminderForUpcoming() {
        List<Interview> interviews = interviewRepository.findAll();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        interviews.stream()
                .filter(i -> i.getScheduleTime() != null && i.getScheduleTime().equals(tomorrow))
                .forEach(i -> log.info("Sending reminder for interview id: {}", i.getId()));
    }

    //Convert entity to dto
    private InterviewDTO convertToDTO(@NotNull Interview interview) {
        return InterviewDTO.builder()
                .id(interview.getId())
                .feedback(interview.getFeedback())
                .jobId(interview.getJob() != null ? interview.getJob().getId() : 0L)
                .scheduleTime(interview.getScheduleTime() != null
                        ? interview.getScheduleTime().atStartOfDay()
                        : null)
                .status(interview.isStatus())
                .locations(interview.getLocations())
                .result(interview.getResult())
                .build();
    }

    //convert dto to entity
    private Interview convertToEntity(@NotNull InterviewDTO dto) {
        return Interview.builder()
                .id(dto.getId())
                .feedback(dto.getFeedback())
                .scheduleTime(dto.getScheduleTime() != null ? dto.getScheduleTime().toLocalDate() : null)
                .status(dto.isStatus())
                .locations(dto.getLocations())
                .result(dto.getResult())
                .build();
    }


}
