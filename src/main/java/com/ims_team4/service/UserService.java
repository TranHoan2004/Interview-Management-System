package com.ims_team4.service;

import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserByEmail(String email) throws Exception;

    UserDTO saveUser(UserDTO userDTO);

    List<String> getAllEmail() throws Exception;

    UserDTO getUserById(int id);

    UserDTO getUserWithId(long id);

    Users getUser(long id);

    Optional<UserDTO> getManagerById(Long id);

    Users createUser(UserDTO userDTO);
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Transactional
    void deleteUserById(Long userId);

    Users findByEmail(String email);
}
