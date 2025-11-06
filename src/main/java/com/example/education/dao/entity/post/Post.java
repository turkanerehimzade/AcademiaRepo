package com.example.education.dao.entity.post;

import com.example.education.dao.entity.BaseEntity;
import com.example.education.dao.entity.discussion.Discussion;
import com.example.education.dao.entity.student.Student;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@ToString
@Table(name = "posts")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {
    @ManyToOne(optional=false) @JoinColumn(name="discussion_id")
    private Discussion discussion;

    @ManyToOne(optional=false) @JoinColumn(name="student_id")
    private Student student;

    @Column(nullable=false, columnDefinition="text") private String content;
}
