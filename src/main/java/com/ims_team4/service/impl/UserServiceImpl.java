package com.ims_team4.service.impl;

import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.Users;
import com.ims_team4.repository.UserRepository;
import com.ims_team4.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
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
        Optional<Users> list = userRepository.findByEmail(email);
        if (list.isEmpty()) {
            throw new Exception("Email not found");
        }
        return list.map(this::convertToDTO).stream().toList();
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        // Chuyển đổi từ UserDTO sang User (Model) để làm việc với Repository
        Users user = convertToEntity(userDTO);

        // Lưu user vào database và nhận lại User (Model)
        Users savedUser = userRepository.save(user);

        // Chuyển đổi từ User (Model) sang UserDTO để trả về cho controller
        return convertToDTO(savedUser);
    }

    @Override
    public List<String> getAllEmail() throws Exception {
        List<String> list = userRepository.getEmails();
        if (list.isEmpty()) {
            throw new Exception("Email not found");
        }
        return list;
    }

    @Override
    public UserDTO getUserById(int id) {
        return convertToDTO(userRepository.getUserById(id));
    }

    @Override
    public UserDTO getUserWithId(long id) {
        return convertToDTO(userRepository.getUserById(id));
    }

    @Override
    public Users getUser(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public Optional<UserDTO> getManagerById(Long id) {
        Users user = userRepository.getUserById(id);
        return user != null ? Optional.of(convertToDTO(user)) : Optional.empty();
    }

    @NotNull
    private Users convertToEntity(@NotNull UserDTO userDTO) {
        Users user;

        if (userDTO.getId() != null) {
            // Lấy entity từ database nếu có ID
            user = userRepository.findById(userDTO.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } else {
            // Tạo mới nếu không có ID
            user = new Users();
        }

        user.setDob(userDTO.getDob());
        user.setGender(userDTO.getGender());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setAvatar(userDTO.getAvatar());
        user.setFullname(userDTO.getFullname());
        user.setPhone(userDTO.getPhone());
        user.setStatus(userDTO.isStatus());
        user.setNote(userDTO.getNote());

        return user;
    }

    private UserDTO convertToDTO(@NotNull Users user) {
        Logger log = Logger.getLogger(this.getClass().getName());
        log.info("convertToDTO, User: " + user);
//        log.info(user.getEmployee().getRole().name());
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
                .role(user.getEmployee() == null ? null : user.getEmployee().getRole())
                .build();
    }
}
