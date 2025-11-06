package com.example.education.dao.entity.discussion;

import com.example.education.dao.entity.BaseEntity;
import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.instructor.Instructor;
import com.example.education.dao.entity.post.Post;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@ToString
@Table(name = "discussions")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Discussion extends BaseEntity {
    @Column(nullable=false) private String topic;
    @ManyToOne(optional=false) @JoinColumn(name="course_id")
    private Course course;

    @ManyToOne(optional=false) @JoinColumn(name="instructor_id")
    private Instructor instructor; // Instructor -> User (ad/soyad buradan gələcək)

    @OneToMany(mappedBy="discussion", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Post> posts = new ArrayList<>();
}
