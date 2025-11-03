package com.example.education.service;

import com.example.education.dao.entity.User;
import com.example.education.dao.entity.student.Student;
import com.example.education.dao.repository.StudentRepository;
import com.example.education.dao.repository.UserRepository;
import com.example.education.dto.request.student.StudentAttachRequest;
import com.example.education.dto.request.student.StudentCreateRequest;
import com.example.education.dto.response.student.StudentResponse;
import com.example.education.enums.RoleName;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {
        private final UserRepository userRepository;
        private final StudentRepository studentRepository;
        private final PasswordEncoder passwordEncoder; // BCryptPasswordEncoder bean

        private StudentResponse toResponse(User user, Student s){
            String fullName = user.getFirstName() + " " + user.getLastName();
            return new StudentResponse(
                    user.getId(), user.getEmail(), fullName.trim(),
                    s.getFaculty(), s.getGroupName(), s.getDateOfBirth(), s.getEnrollmentDate()
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
            user.setRole(RoleName.USER);
            // save user first (gets id)
            user = userRepository.save(user);

            Student s = new Student();
            s.setUser(user);          // @MapsId
            s.setFaculty(req.faculty());
            s.setGroupName(req.groupName());
            s.setDateOfBirth(req.dateOfBirth());
            s.setEnrollmentDate(req.enrollmentDate());
            studentRepository.save(s);

            return toResponse(user, s);
        }


        @Transactional
        public StudentResponse attachStudentToExistingUser(StudentAttachRequest req) {
            User user = userRepository.findByEmailIgnoreCase(req.email())
                    .orElseThrow(() -> new EntityNotFoundException("User tapılmadı: " + req.email()));

            if (studentRepository.existsByUserId(user.getId())) {
                throw new IllegalStateException("Bu user artıq student profilinə sahibdir.");
            }

            Student s = new Student();
            s.setUser(user); // @MapsId
            s.setFaculty(req.faculty());
            s.setGroupName(req.groupName());
            s.setDateOfBirth(req.dateOfBirth());
            s.setEnrollmentDate(req.enrollmentDate());
            studentRepository.save(s);

            return toResponse(user, s);
        }
    }

