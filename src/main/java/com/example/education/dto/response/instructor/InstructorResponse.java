package com.example.education.dto.response.instructor;

public record InstructorResponse(
        Long id,
        String email,
        String fullName,
        String specialization,
        String phoneNumber
) {
}
