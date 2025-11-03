package com.example.education.dto.response.student;

import java.time.LocalDate;

public record StudentResponse(
        Long userId,
        String email,
        String fullName,
        String faculty,
        String groupName,
        LocalDate dateOfBirth,
        LocalDate enrollmentDate
) {
}
