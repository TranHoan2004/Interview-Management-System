package com.ims_team4.service.impl;

import com.ims_team4.dto.UserDTO;
import com.ims_team4.model.Users;
import com.ims_team4.repository.UserRepository;
import com.ims_team4.service.UserService;
import jakarta.transaction.Transactional;
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
        Users user = new Users();
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

    @Override
    @Transactional
    public Users createUser(UserDTO userDTO) {
        // ✅ Kiểm tra email đã tồn tại chưa
        Optional<Users> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("❌ Email already exists: " + userDTO.getEmail());
        }

        // ✅ Tạo User mới
        Users user = new Users();
        user.setFullname(userDTO.getFullname());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setDob(userDTO.getDob());
        user.setAddress(userDTO.getAddress());
        user.setGender(userDTO.getGender());
        user.setStatus(true);
        user.setNote(userDTO.getNote());

        // ✅ Lưu vào DB
//        System.out.println("✅ User created: " + user.getId());
        return userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }


    @Override
    @Transactional
    public void deleteUserById(Long userId) {
//        System.out.println("🔍 Checking user before delete: " + userId);
        if (userRepository.checkExistsById(userId)) {
            userRepository.removeById(userId);
//            System.out.println("🗑️ User deleted!");
        }
//        else {
//            System.out.println("⚠️ User not found, skipping deletion.");
//        }
    }




}
