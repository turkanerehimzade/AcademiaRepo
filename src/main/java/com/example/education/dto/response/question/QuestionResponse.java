package com.example.education.dto.response.question;
import com.example.education.dao.entity.material.Material;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class QuestionResponse {
    private String question;
    private List<String> options;
    private String answer;
}
