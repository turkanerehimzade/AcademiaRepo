package com.example.education.dto.request.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record StudentAttachRequest(
        @Email @NotBlank String email,
        Long courseId,
        LocalDate enrollmentDate
) {
}
