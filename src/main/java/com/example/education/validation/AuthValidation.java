package com.example.education.validation;

import com.example.education.dao.repository.UserRepository;
import com.example.education.dto.request.auth.ChangePasswordRequest;
import com.example.education.enums.Regex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class AuthValidation {
    private final UserRepository userRepository;

    public void validateChangePassword(ChangePasswordRequest req) {
        List<String> messages = new ArrayList<>();

        if (!userRepository.existsByEmail(req.getEmail())) {
            messages.add("Email address does not exist");
        }
        if (req.getNewPassword() == null || req.getNewPassword().isBlank()) {
            messages.add("Password cannot be empty");
        }
        if (!req.getNewPassword().equals(req.getConfirmPassword())) {
            messages.add("Confirm password does not match");
        }
        if (req.getOldPassword() != null && req.getOldPassword().equals(req.getNewPassword())) {
            messages.add("New password cannot be same as old password");
        }
        if (req.getNewPassword() != null &&
                !Pattern.matches(Regex.PASSWORD_REGEX.getValidation(), req.getNewPassword())) {
            messages.add("Password does not meet required format");
        }
        if (!messages.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", messages));
        }
    }
}


