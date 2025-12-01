package com.example.education.dao.repository;

import com.example.education.dao.entity.student.Student;
import com.example.education.enums.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllByUserId(Long userId);
    boolean existsByUserIdAndCourses_IdAndStatus(Long userId,
                                                 Long courseId,
                                                 StudentStatus status);

    @Query("select s from Student s " +
            "left join fetch s.courses c " +
            "where s.id = :id")
    Optional<Student> findByIdWithCourses(@Param("id") Long id);

}
