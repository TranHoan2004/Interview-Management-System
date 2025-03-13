package com.ims_team4.repository;

import com.ims_team4.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
    List<String> getEmails();

    Optional<Users> findByEmail(String email);

    List<Users> getAllUser();

    Users getUserById(long id);

    @Query("SELECT u FROM Users u WHERE u.id = :id")
    Optional<Users> findById(@Param("id") Long id);
}
