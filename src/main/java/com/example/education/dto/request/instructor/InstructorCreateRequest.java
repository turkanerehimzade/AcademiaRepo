package com.example.education.dto.request.instructor;

import com.example.education.dao.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record InstructorCreateRequest (
        @Email @NotBlank String email,
        @NotBlank String password,         // Admin yaradırsa müvəqqəti parola verə bilər
        @NotBlank String firstName,
        @NotBlank String lastName,
        String specialization,
        String phoneNumber
){
}
