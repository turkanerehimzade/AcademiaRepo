package com.example.education.dao.entity.course;

import com.example.education.dao.entity.BaseEntity;
import com.example.education.dao.entity.discussion.Discussion;
import com.example.education.dao.entity.student.Student;
import com.example.education.dao.entity.topic.Topic;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString
@Table(name = "courses")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity {
    private String title;
    private String description;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics;
    private String fotoUrl;
    private String groupName;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Discussion> discussions = new ArrayList<>();
    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();
}
