package com.example.education.dto.response.material;

import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.question.Question;
import com.example.education.dto.response.question.QuestionResponse;
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
public class MaterialResponse {
    private Long id;
    private String title;
    private String description;
    private String content;
    private List<QuestionResponse> question;
    private String recordUrl;
}
