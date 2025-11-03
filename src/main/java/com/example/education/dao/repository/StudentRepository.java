package com.example.education.dao.repository;

import com.example.education.dao.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
//    Optional<Student> findByEmail(String email);
//    Boolean existsByEmail(String email);
    boolean existsByUserId(Long userId);

}
