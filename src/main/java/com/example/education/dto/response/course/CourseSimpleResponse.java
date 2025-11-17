package com.example.education.dto.response.course;

public record CourseSimpleResponse(Long id,
                                   String title,
                                   String description,
                                   String groupName
) {
}
