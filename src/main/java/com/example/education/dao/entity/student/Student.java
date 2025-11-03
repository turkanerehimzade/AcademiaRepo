package com.example.education.dao.entity.student;

import com.example.education.dao.entity.BaseEntity;
import com.example.education.dao.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Data
@Entity
@ToString
@Table(name = "students")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Student extends BaseEntity {
    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private User user;
    private String faculty;
    private String groupName;
    private LocalDate dateOfBirth;
    private LocalDate enrollmentDate;
    private String status;
}
