package com.example.education.dao.repository;

import com.example.education.dao.entity.course.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findCourseById(Long id);
    boolean existsByGroupName(String groupName);

    @EntityGraph(attributePaths = {
            "discussions",
            "discussions.instructor",
            "discussions.instructor.user",
            "discussions.posts",
            "discussions.posts.student",
            "discussions.posts.student.user"
    })
    Optional<Course> findWithAllById(Long id);
}
