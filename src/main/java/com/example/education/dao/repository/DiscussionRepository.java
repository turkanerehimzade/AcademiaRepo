package com.example.education.dao.repository;

import com.example.education.dao.entity.discussion.Discussion;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    // Course üzrə bütün discussion-ları dərin yüklə
    @Query("""
           select distinct d from Discussion d
             join fetch d.instructor i
             join fetch i.user iu
             left join fetch d.posts p
             left join fetch p.student s
             left join fetch s.user su
           where d.course.id = :courseId
           """)
    List<Discussion> findDeepByCourseId(@Param("courseId") Long courseId);

    // Tək discussion-u dərin yüklə
    @Query("""
           select d from Discussion d
             join fetch d.instructor i
             join fetch i.user iu
             left join fetch d.posts p
             left join fetch p.student s
             left join fetch s.user su
           where d.id = :id
           """)
    Optional<Discussion> findWithPostsAndStudentsAndUsersById(@Param("id") Long id);
}
