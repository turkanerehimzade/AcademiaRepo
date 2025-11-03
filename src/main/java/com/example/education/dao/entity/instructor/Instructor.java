package com.example.education.dao.entity.instructor;

import com.example.education.dao.entity.BaseEntity;
import com.example.education.dao.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@ToString
@Table(name = "instructors")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Instructor extends BaseEntity {
    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private User user;
    private String specialization;
    private String phoneNumber;
}
