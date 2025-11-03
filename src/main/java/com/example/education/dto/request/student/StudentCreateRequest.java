package com.example.education.dto.request.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record StudentCreateRequest(
        @Email @NotBlank String email,
        @NotBlank String password,         // Admin yaradırsa müvəqqəti parola verə bilər
        @NotBlank String firstName,
        @NotBlank String lastName,
        String phoneNumber,
        LocalDate dateOfBirth,
        String faculty,
        String groupName,
        LocalDate enrollmentDate) {
}
