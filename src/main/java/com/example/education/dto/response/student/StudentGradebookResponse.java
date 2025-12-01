package com.example.education.dto.response.student;

import com.example.education.dto.response.course.CourseWithGradebookResponse;

import java.time.OffsetDateTime;
import java.util.List;

public record StudentGradebookResponse(
        StudentMiniResponse student,
        List<CourseWithGradebookResponse> courses,
        OffsetDateTime lastUpdated
) {
}
