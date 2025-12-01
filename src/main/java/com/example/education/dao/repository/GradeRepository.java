package com.example.education.dao.repository;

import com.example.education.dao.entity.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId);
}
