package com.example.education.dto.response.topic;

import com.example.education.dto.response.material.MaterialResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TopicResponse {
    private Long id;
    private String title;
    private String description;
    private List<MaterialResponse> materials;
}
