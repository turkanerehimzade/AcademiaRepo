package com.example.education.service;

import com.example.education.dao.entity.User;
import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.student.Student;
import com.example.education.dao.repository.CourseRepository;
import com.example.education.dao.repository.StudentRepository;
import com.example.education.dao.repository.UserRepository;
import com.example.education.dto.request.student.StudentAttachRequest;
import com.example.education.dto.request.student.StudentCreateRequest;
import com.example.education.dto.response.course.CourseSimpleResponse;
import com.example.education.dto.response.student.StudentResponse;
import com.example.education.enums.RoleName;
import com.example.education.enums.StudentStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;

    private StudentResponse toResponse(User user, Student s) {
        String fullName = user.getFirstName() + " " + user.getLastName();

        List<CourseSimpleResponse> courseResponses = s.getCourses()
                .stream()
                .map(course -> new CourseSimpleResponse(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription(),
                        course.getGroupName()
                ))
                .toList();

        return new StudentResponse(
                s.getId(),
                user.getEmail(),
                fullName.trim(),
                s.getDateOfBirth(),
                s.getEnrollmentDate(),
                courseResponses
        );
    }

    @Transactional
    public StudentResponse createStudentWithUser(StudentCreateRequest req) {

        if (userRepository.existsByEmailIgnoreCase(req.email())) {
            throw new IllegalArgumentException("Email artıq mövcuddur: " + req.email());
        }

        User user = new User();
        user.setEmail(req.email().trim());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setFirstName(req.firstName());
        user.setLastName(req.lastName());
        user.setRole(RoleName.STUDENT);
        user = userRepository.save(user);

        Course course = courseRepository.findById(req.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Course tapılmadı: " + req.courseId()));

        Student s = new Student();
        s.setUser(user);
        s.setId(user.getId());
        s.setStatus(StudentStatus.STUDYING);
        s.setDateOfBirth(req.dateOfBirth());
        s.setEnrollmentDate(req.enrollmentDate());

        s.getCourses().add(course);

        studentRepository.save(s);

        return toResponse(user, s);
    }

    @Transactional
    public StudentResponse attachStudentToExistingUser(StudentAttachRequest req) {
        User user = userRepository.findByEmailIgnoreCase(req.email())
                .orElseThrow(() -> new EntityNotFoundException("User tapılmadı: " + req.email()));

        Course course = courseRepository.findById(req.courseId())
                .orElseThrow(() -> new EntityNotFoundException("Course tapılmadı: " + req.courseId()));

        boolean alreadyStudying = studentRepository.existsByUserIdAndCourses_IdAndStatus(
                user.getId(),
                course.getId(),
                StudentStatus.STUDYING
        );

        if (alreadyStudying) {
            throw new IllegalStateException(
                    "Bu istifadəçi artıq bu kursda 'STUDYING' statusu ilə qeydiyyatdadır."
            );
        }

        Student s = new Student();
        s.setUser(user);
        s.setDateOfBirth(user.getStudents().getFirst().getDateOfBirth());
        s.setEnrollmentDate(req.enrollmentDate());
        s.setStatus(StudentStatus.STUDYING);

        s.getCourses().add(course);

        studentRepository.save(s);

        return toResponse(user, s);
    }

}

