package com.ims_team4.service;

import com.ims_team4.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    List<UserDTO> getUserByEmail(String email) throws Exception;

    UserDTO saveUser(UserDTO userDTO);
}
