package com.ims_team4.repository;

import com.ims_team4.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // <editor-fold desc="Code bởi @Duc Long- getALlUser">
    List<User> getAllUser();
    // </editor-fold>
}
