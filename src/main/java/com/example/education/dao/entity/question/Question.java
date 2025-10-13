package com.example.education.dao.entity.question;

import com.example.education.dao.entity.BaseEntity;
import com.example.education.dao.entity.material.Material;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString
@Table(name = "questions")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;
    private String question;

    @ElementCollection
    @CollectionTable(
            name = "question_options",
            joinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "option_text")
    private List<String> options = new ArrayList<>();
    private String answer;
}
