package com.example.education.dto.response.course;

import com.example.education.dto.response.topic.TopicResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private String groupName;
    private List<TopicResponse> topics;
}
