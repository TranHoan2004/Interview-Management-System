package com.ims_team4.service;

import com.ims_team4.dto.SkillDTO;
import com.ims_team4.model.Skill;

import java.util.List;
import java.util.Set;

public interface SkillService {
    List<SkillDTO> getAllSkill();

    Set<Skill> getSkillsByName(List<String> skillsName);

    Set<Skill> getSkillsByIds(List<Long> skillIds);
}
