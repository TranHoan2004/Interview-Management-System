package com.ims_team4.repository;

import com.ims_team4.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("SELECT s FROM Skill s")
    List<Skill> getAllSkill();
    List<Skill> findByNameIn(Set<String> names);

    Set<Skill> findByIdIn(Set<Long> ids);

}
