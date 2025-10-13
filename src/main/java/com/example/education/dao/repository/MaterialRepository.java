package com.example.education.dao.repository;

import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByTopicId(Long id);
}
