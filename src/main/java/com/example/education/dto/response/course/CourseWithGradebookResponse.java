package com.example.education.dto.response.course;

import com.example.education.dto.response.gradebook.GradebookResponse;

public record CourseWithGradebookResponse(
        Long id,
        String title,
        String description,
        GradebookResponse gradebook
) {
}
