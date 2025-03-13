package com.ims_team4.service;

import com.ims_team4.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();

    List<UserDTO> getUserByEmail(String email) throws Exception;

    UserDTO saveUser(UserDTO userDTO);

    List<String> getAllEmail() throws Exception;

    UserDTO getUserById(int id);

    Optional<UserDTO> getManagerById(Long id);
}
