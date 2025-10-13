package com.example.education.dao.entity.topic;

import com.example.education.dao.entity.BaseEntity;
import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.material.Material;
import com.example.education.dao.entity.question.Question;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@ToString
@Table(name = "topics")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Topic extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private String title;
    private String description;
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material> materials;
}
