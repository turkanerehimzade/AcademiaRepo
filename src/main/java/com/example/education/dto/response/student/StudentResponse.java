package com.example.education.dto.response.student;


import com.example.education.dto.response.course.CourseSimpleResponse;

import java.time.LocalDate;
import java.util.List;

public record StudentResponse(
        Long id,
        String email,
        String fullName,
        LocalDate dateOfBirth,
        LocalDate enrollmentDate,
        List<CourseSimpleResponse> courses
) {
}
