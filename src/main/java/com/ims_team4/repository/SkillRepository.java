package com.ims_team4.repository;

import com.ims_team4.model.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SkillRepository extends CrudRepository<Skill, Long> {
    List<Skill> getAllSkill();
}
