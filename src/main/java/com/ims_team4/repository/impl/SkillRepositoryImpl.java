package com.ims_team4.repository.impl;

import com.ims_team4.model.Skill;
import com.ims_team4.repository.SkillRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class SkillRepositoryImpl{

    private final EntityManager em;

    public SkillRepositoryImpl(EntityManager em) {
        this.em = em;
    }


}
