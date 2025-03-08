package com.ims_team4.repository.impl;

import com.ims_team4.model.Level;
import com.ims_team4.repository.LevelRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class LevelRepositoryImpl {

    private final EntityManager em;

    public LevelRepositoryImpl(EntityManager em) {
        this.em = em;
    }


}
