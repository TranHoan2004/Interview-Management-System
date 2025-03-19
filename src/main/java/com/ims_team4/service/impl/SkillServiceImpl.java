package com.ims_team4.service.impl;

import com.ims_team4.dto.SkillDTO;
import com.ims_team4.model.Skill;
import com.ims_team4.repository.SkillRepository;
import com.ims_team4.service.SkillService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<SkillDTO> getAllSkill() {
        return skillRepository.getAllSkill().stream()
                .map(skill -> new SkillDTO(skill.getId(), skill.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Skill> getSkillsByName(List<String> skillsName) {
        if (skillsName == null || skillsName.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> skillsNameSet = new HashSet<>(skillsName);
        List<Skill> skills = skillRepository.findByNameIn(skillsNameSet);
        return new HashSet<>(skills != null ? skills : Collections.emptyList());
    }

    private SkillDTO convertToDTO(@NotNull Skill skill) {
        return SkillDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }
}
