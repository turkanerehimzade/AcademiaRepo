package com.example.education.dto.request.question;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QuestionRequest {
    private Long materialId;
    private String question;
    private List<String> options;
    private String answer;
}
