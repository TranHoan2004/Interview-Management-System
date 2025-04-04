package com.ims_team4.service.impl;

import com.ims_team4.dto.CandidateDTO;
import com.ims_team4.model.*;
import com.ims_team4.model.utils.CandidateStatus;
import com.ims_team4.repository.*;
import com.ims_team4.service.CandidateService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
// Duc Long
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final EntityManager em;
    private static final Logger logger = Logger.getLogger(CandidateServiceImpl.class.getName());


    public CandidateServiceImpl(CandidateRepository candidateRepository, EntityManager em) {
        this.candidateRepository = candidateRepository;
        this.em = em;
    }

    private CandidateDTO convertToDTO(@NotNull Candidate candidate) {
        return CandidateDTO.builder()
                .userId(candidate.getId())
//                .skill(candidate.getSkills())
//                .highestEducation(candidate.getHighestLevel().getId())
                .experience(candidate.getExperience())
//                .position(candidate.getPosition())
                .cv(candidate.getCv())
                .build();
    }

    @Override
    public List<CandidateDTO> getCandidateByUserId(Long userId) {
        List<Candidate> candidates = new ArrayList<>();
        candidateRepository.getCandidateByUserId(userId).ifPresent(candidates::add);        return candidates.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Candidate> findCandidateByUserId(Long userId) {
        return candidateRepository.getCandidateByUserId(userId); // Giữ nguyên logic
    }


    @Override
    public List<CandidateDTO> getAllCandidate() {
        return candidateRepository.getAllCandidate().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CandidateDTO> getAllCandidateNoBan() {
        return candidateRepository.getAllCandidateNotBan().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CandidateDTO convertToDTO2(Candidate candidate) {
//        System.out.println("🔄 Converting Candidate to CandidateDTO...");

        CandidateDTO dto = CandidateDTO.builder()
//                .id(candidate.getId())
                .userId(candidate.getUser() != null ? candidate.getUser().getId() : null)
                .fullName(candidate.getUser() != null ? candidate.getUser().getFullname() : "N/A")
                .email(candidate.getUser() != null ? candidate.getUser().getEmail() : "N/A")
                .phone(candidate.getUser() != null ? candidate.getUser().getPhone() : "N/A")
                .dob(candidate.getUser() != null && candidate.getUser().getDob() != null ? candidate.getUser().getDob() : null)
                .address(candidate.getUser() != null ? candidate.getUser().getAddress() : "N/A")
                .gender(candidate.getUser() != null ? candidate.getUser().getGender() : -1)
                .experience(candidate.getExperience())
                .positionId(candidate.getPosition() != null ? candidate.getPosition().getId().intValue() : 0)
                .positionName(candidate.getPosition() != null ? candidate.getPosition().getName() : "N/A")
                .cv(candidate.getCv() != null ? candidate.getCv() : new byte[0])
                .highestEducation(candidate.getHighestLevel() != null ? candidate.getHighestLevel().getId() : 0)
                .status(candidate.getStatus() != null ? candidate.getStatus().toString() : "Unknown")
                .skills(candidate.getSkills() != null
                        ? candidate.getSkills().stream().map(Skill::getName).collect(Collectors.toSet())
                        : Collections.emptySet())
                .recruiter(candidate.getEmployee() != null && candidate.getEmployee().getUser() != null
                        ? candidate.getEmployee().getUser().getFullname()
                        : "N/A")
                .note(candidate.getUser() != null ? candidate.getUser().getNote() : "N/A")
                .build();

//        System.out.println("🎯 CandidateDTO After Mapping: " + dto);
        return dto;
    }


    @Transactional
    @Override
    public boolean addCandidate(CandidateDTO candidateDTO, HighestLevel highestLevel, Position position, Employee recruiter, Users createdUser) {
//        System.out.println("🔄 addCandidate() called with User ID: " + candidateDTO.getUserId());
        try {
//            System.out.println("🔄 Creating new Candidate for User ID: " + candidateDTO.getUserId());

            // ✅ Kiểm tra User ID hợp lệ
            if (candidateDTO.getUserId() == null || candidateDTO.getUserId() <= 0) {
                throw new RuntimeException("❌ Error: User ID is required and must be valid!");
            }

            // ✅ Kiểm tra CV có null không?
            if (candidateDTO.getCv() == null || candidateDTO.getCv().length == 0) {
                throw new RuntimeException("❌ Error: CV file is required!");
            }

            // ✅ Tạo Candidate mới
            Candidate candidate = new Candidate();
            candidate.setId(candidateDTO.getUserId());
            candidate.setExperience(candidateDTO.getExperience());
            candidate.setCv(candidateDTO.getCv());
            candidate.setStatus(CandidateStatus.OPEN);
            candidate.setHighestLevel(highestLevel);
            candidate.setPosition(position);
            candidate.setEmployee(recruiter);
            candidate.setUser(createdUser);

//            System.out.println("✅ Assigned Highest Level: " + highestLevel.getName());
//            System.out.println("✅ Assigned Position: " + position.getName());
//            System.out.println("✅ Assigned Employee (Recruiter): " + recruiter.getWorkingName());


            // ✅ Lưu Candidate vào Database
            candidateRepository.save(candidate);
//            System.out.println("✅ Candidate saved successfully!");
            return true;
        } catch (Exception e) {
//            System.err.println("❌ Error while saving candidate: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<CandidateDTO> getAllCandidate2() {
        List<Candidate> candidates = candidateRepository.getAllCandidate();
        return candidates.stream()
                .map(this::convertToDTO2)
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDTO getCandidateDetails(Long userId) {
        Optional<Candidate> candidate = candidateRepository.findByUserId(userId);
        return candidate.map(this::convertToDTO2).orElse(null);
    }

    @Override
    public CandidateDTO getCandidateById2(Long userId) {
//        System.out.println("🟢 Request for Candidate Details with UserID: " + userId);

        Optional<Candidate> candidateOpt = candidateRepository.findByUserId(userId);

        if (candidateOpt.isEmpty()) {
//            System.out.println("❌ Candidate not found!");
            return new CandidateDTO(); // Trả về DTO trống tránh null
        }

        Candidate candidate = candidateOpt.get();
//        System.out.println("✅ Candidate found: " + candidate.getUser().getFullname());

        // 👉 Đảm bảo ánh xạ bằng convertToDTO2()
        CandidateDTO dto = convertToDTO2(candidate);

//        System.out.println("📢 Passing CandidateDTO to View: " + dto);
        return dto;
    }


    @Override
    @Transactional
    public boolean deleteCandidateByUserId(Long userId) {
//        System.out.println("🔍 Checking candidate with userId: " + userId);

        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> {
//                    System.out.println("❌ Candidate not found!");
                    return new IllegalArgumentException("Candidate with userId " + userId + " not found.");
                });

        // 🛑 Kiểm tra nếu trạng thái không phải "OPEN"
        if (!CandidateStatus.OPEN.equals(candidate.getStatus())) {
//            System.out.println("⛔ Cannot delete! Candidate is not in 'OPEN' status. Current status: " + candidate.getStatus());
            return false;
        }

//        System.out.println("✅ Candidate is 'OPEN', proceeding to delete...");
        candidateRepository.deleteByUserId(userId);

        // 🚀 Đảm bảo Hibernate giải phóng reference của Candidate
        em.flush();
        em.clear(); // 🛑 Giúp tránh lỗi liên quan đến bản ghi đã xóa

//        System.out.println("🗑️ Candidate deleted!");
        return true;
    }


    @Transactional
    @Override
    public void addSkillsToCandidate(Candidate candidate, Set<Skill> skills) {
        if (candidate == null) {
            throw new RuntimeException("Candidate is null, cannot add skills.");
        }

        if (skills == null || skills.isEmpty()) {
            throw new RuntimeException("No skills provided.");
        }

        // Đảm bảo Candidate có danh sách kỹ năng trước khi thêm
        if (candidate.getSkills() == null) {
            candidate.setSkills(new HashSet<>());
        }

        candidate.getSkills().addAll(skills);

        // Lưu Candidate với kỹ năng mới vào DB
        candidateRepository.save(candidate);
    }


    @Override
    @Transactional
    public boolean toggleBanStatus(Long userId) {
        Optional<Candidate> candidateOpt = candidateRepository.findByUserId(userId);

        if (candidateOpt.isEmpty()) {
            return false;
        }

        Candidate candidate = candidateOpt.get();

        // Toggle status: OPEN → BANNED, BANNED → OPEN
        candidate.setStatus(candidate.getStatus() == CandidateStatus.BANNED ? CandidateStatus.OPEN : CandidateStatus.BANNED);

        candidateRepository.save(candidate);
        return true;
    }

    @Transactional
    @Override
    public boolean updateCandidate(CandidateDTO candidateDTO, HighestLevel highestLevel,
                                   Set<Skill> updatedSkills, Users user, Position newPosition, Employee newRecruiter) {
        logger.info("🔄 [UPDATE] Processing Candidate ID: " + candidateDTO.getUserId());

        Optional<Candidate> candidateOpt = candidateRepository.findByUserId(candidateDTO.getUserId());
        if (candidateOpt.isEmpty()) {
            logger.severe("❌ Candidate not found in database. User ID: " + candidateDTO.getUserId());
            return false;
        }

        Candidate candidate = candidateOpt.get();
        logger.info("✅ Candidate found: " + user.getFullname());

        // ✅ Cập nhật thông tin cá nhân trong Users
        candidate.setUser(user);
        candidate.setExperience(candidateDTO.getExperience());
        candidate.setStatus(CandidateStatus.valueOf(candidateDTO.getStatus()));
        candidate.setHighestLevel(highestLevel);

        // ✅ Cập nhật CV nếu có
        if (candidateDTO.getCv() != null && candidateDTO.getCv().length > 0) {
            candidate.setCv(candidateDTO.getCv());
        }

        // ✅ Cập nhật danh sách Skills
        candidate.setSkills(updatedSkills);

        // ✅ Cập nhật Recruiter nếu thay đổi
        logger.info("🔍 Current Recruiter: " + (candidate.getEmployee() != null ? candidate.getEmployee().getId() : "None"));
        logger.info("🆕 New Recruiter: " + (newRecruiter != null ? newRecruiter.getId() : "None"));

        if (newRecruiter != null && (candidate.getEmployee() == null || !newRecruiter.getId().equals(candidate.getEmployee().getId()))) {
            candidate.setEmployee(newRecruiter);
            logger.info("📌 Recruiter updated: " + newRecruiter.getUser().getFullname());
        } else {
            logger.info("🔍 Recruiter remains unchanged.");
        }


        // ✅ Cập nhật Position nếu thay đổi
        if (newPosition != null && !newPosition.equals(candidate.getPosition())) {
            candidate.setPosition(newPosition);
            logger.info("📌 Position updated: " + newPosition.getName());
        }

        // ✅ Lưu Candidate
        candidateRepository.save(candidate);
        logger.info("✅ [SUCCESS] Candidate updated in database!");

        return true;
    }

    @Override
    public int getTotalCandidates() {
        return (int) candidateRepository.countAllCandidates();
    }
    @Override
    public int getOpenCandidates() {
        return candidateRepository.countByStatus(CandidateStatus.OPEN);
    }
    @Override
    public int getWaitingForInterviewCandidates() {
        return candidateRepository.countByStatus(CandidateStatus.WAITING_FOR_INTERVIEW);
    }
    @Override
    public int getPassedInterviewCandidates() {
        return candidateRepository.countByStatus(CandidateStatus.PASSED_INTERVIEW);
    }



}
