package com.example.education.dto.request.material;

import com.example.education.dto.request.question.QuestionRequest;
import com.example.education.dto.response.question.QuestionResponse;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MaterialRequest {
    private Long topicId;
    private String title;
    private String description;
    private String content;
    private String recordUrl;
}
