package com.example.education.service;


import com.example.education.dao.entity.User;
import com.example.education.dao.repository.UserRepository;
import com.example.education.dto.request.UserRequest;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.user.UserResponse;
import com.example.education.enums.ResponseCode;
import com.example.education.enums.RoleName;
import com.example.education.mapper.UserMapper;
import com.example.education.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserValidation validation;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SuccessResponse<UserResponse> createUser(UserRequest userRequest) {
        validation.validateUser(userRequest);
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encodedPassword);
        User user =UserMapper.INSTANCE.toEntity(userRequest);
        user.setRole(RoleName.USER);
        userRepository.save(user);
        return SuccessResponse.createSuccessResponse(UserMapper.INSTANCE.toResponse(user), ResponseCode.SUCCESS);
    }
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User with this username not found"));
    }
    public static String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}
