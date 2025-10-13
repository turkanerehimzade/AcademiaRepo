package com.example.education.dao.entity.material;

import com.example.education.dao.entity.BaseEntity;
import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.question.Question;
import com.example.education.dao.entity.topic.Topic;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString
@Table(name = "materials")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Material extends BaseEntity {
    private String title;
    private String description;
    private String content;
    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> question;
    private String recordUrl;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

}
