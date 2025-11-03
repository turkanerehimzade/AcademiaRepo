package com.example.education.dto.response.instructor;

import java.time.LocalDate;

public record InstructorResponse(
        Long userId,
        String email,
        String fullName,
        String specialization,
        String phoneNumber
) {
}
