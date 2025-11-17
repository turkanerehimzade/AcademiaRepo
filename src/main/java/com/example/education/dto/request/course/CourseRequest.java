package com.example.education.dto.request.course;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class CourseRequest {
    private String title;
    private String description;
    private String groupName;
}
