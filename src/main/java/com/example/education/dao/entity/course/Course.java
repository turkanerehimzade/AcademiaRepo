package com.example.education.dao.entity.course;

import com.example.education.dao.entity.BaseEntity;
import com.example.education.dao.entity.discussion.Discussion;
import com.example.education.dao.entity.material.Material;
import com.example.education.dao.entity.topic.Topic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    @OneToMany(mappedBy="course", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Discussion> discussions = new ArrayList<>();
}
