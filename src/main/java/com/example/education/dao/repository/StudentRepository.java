package com.example.education.dao.repository;

import com.example.education.dao.entity.student.Student;
import com.example.education.enums.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByUserId(Long userId);
    boolean existsByUserIdAndCourses_IdAndStatus(Long userId,
                                                 Long courseId,
                                                 StudentStatus status);

}
