package com.example.education.dao.entity.instructor;

import com.example.education.dao.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@Table(name = "instructors")
@NoArgsConstructor
@AllArgsConstructor
public class Instructor  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    private String specialization;
    private String phoneNumber;
}
