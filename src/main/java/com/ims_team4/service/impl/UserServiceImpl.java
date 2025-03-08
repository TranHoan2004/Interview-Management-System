package com.ims_team4.service.impl;

import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.User;
import com.ims_team4.repository.UserRepository;
import com.ims_team4.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
// Dang Momo
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Lấy danh sách user
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.getAllUser().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<UserDTO> getUserByEmail(String email) throws Exception {
        Optional<User> list = userRepository.findByEmail(email);
        if (list.isEmpty()) {
            throw new Exception("Email not found");
        }
        return list.map(this::convertToDTO).stream().toList();
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        // Chuyển đổi từ UserDTO sang User (Model) để làm việc với Repository
        User user = convertToEntity(userDTO);

        // Lưu user vào database và nhận lại User (Model)
        User savedUser = userRepository.save(user);

        // Chuyển đổi từ User (Model) sang UserDTO để trả về cho controller
        return convertToDTO(savedUser);
    }

    @NotNull
    private User convertToEntity(@NotNull UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setDob(userDTO.getDob());
        user.setGender(userDTO.getGender());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setAvatar(userDTO.getAvatar());
        user.setFullname(userDTO.getFullname());
        user.setPhone(userDTO.getPhone());
        user.setStatus(userDTO.isStatus());
        user.setNote(userDTO.getNote());
        //user.setRole(userDTO.getRole());

        // Nếu có thông tin notification, xử lý thêm ở đây
        // Ví dụ: user.setNotification(someNotification);

        return user;
    }


    private UserDTO convertToDTO(@NotNull User user) {
        return UserDTO.builder()
                .id(user.getId())
                .dob(user.getDob())
                .gender(user.getGender())
                .email(user.getEmail())
                .address(user.getAddress())
                .avatar(user.getAvatar())
                .fullname(user.getFullname())
                .phone(user.getPhone())
                .status(user.isStatus())
                .note(user.getNote())
                //.role(user.getRole())
                .notificationID(user.getNotification() != null ? user.getNotification().getId() : 0)
                .build();
    }
}
