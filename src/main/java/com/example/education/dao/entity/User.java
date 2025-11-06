package com.example.education.dao.entity;

import com.example.education.dao.entity.BaseEntity;
import com.example.education.dao.entity.instructor.Instructor;
import com.example.education.dao.entity.student.Student;
import com.example.education.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@ToString(exclude = {"studentProfile", "instructorProfile"})
@Table(name = "users")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String password;
    @Column(nullable=false) private String firstName;
    @Column(nullable=false) private String lastName;
    private Boolean isEmailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private RoleName role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Student studentProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Instructor instructorProfile;
}
