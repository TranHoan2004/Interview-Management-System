package com.ims_team4.service;

import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();

    List<UserDTO> getUserByEmail(String email) throws Exception;

    UserDTO saveUser(UserDTO userDTO);

    List<String> getAllEmail() throws Exception;

    UserDTO getUserById(int id);

    UserDTO getUserWithId(long id);

    Users getUser(long id);

    Optional<UserDTO> getManagerById(Long id);
}
