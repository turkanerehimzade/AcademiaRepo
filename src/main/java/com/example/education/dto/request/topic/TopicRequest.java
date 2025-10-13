package com.example.education.dto.request.topic;

import com.example.education.dto.response.material.MaterialResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TopicRequest {
    private Long courseId;
    private String title;
    private String description;
}
