package com.ims_team4.service;

import com.ims_team4.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserByEmail(String email);

    UserDTO saveUser(UserDTO userDTO);
}