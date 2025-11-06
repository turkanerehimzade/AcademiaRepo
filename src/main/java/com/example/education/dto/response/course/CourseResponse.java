package com.example.education.dto.response.course;

import com.example.education.dao.entity.material.Material;
import com.example.education.dto.response.discussion.DiscussionResponse;
import com.example.education.dto.response.material.MaterialResponse;
import com.example.education.dto.response.topic.TopicResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
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
    private List<TopicResponse> topics;
}
