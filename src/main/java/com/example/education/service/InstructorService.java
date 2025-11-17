package com.example.education.service;

import com.example.education.dao.entity.User;
import com.example.education.dao.entity.instructor.Instructor;
import com.example.education.dao.repository.InstructorRepository;
import com.example.education.dao.repository.UserRepository;
import com.example.education.dto.request.instructor.InstructorCreateRequest;
import com.example.education.dto.response.instructor.InstructorResponse;
import com.example.education.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;

    private InstructorResponse toResponse(User user, Instructor instructor) {
        String fullName = user.getFirstName() + " " + user.getLastName();
        return new InstructorResponse(
                instructor.getId(), user.getEmail(), fullName.trim(),
                instructor.getSpecialization(), instructor.getPhoneNumber()
        );
    }


    @Transactional
    public InstructorResponse createInstructorWithUser(InstructorCreateRequest req) {
        if (userRepository.existsByEmailIgnoreCase(req.email())) {
            throw new IllegalArgumentException("Email artıq mövcuddur: " + req.email());
        }

        User user = new User();
        user.setEmail(req.email().trim());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setFirstName(req.firstName());
        user.setLastName(req.lastName());
        user.setRole(RoleName.INSTRUCTOR);
        user = userRepository.save(user);

        Instructor instructor = new Instructor();
        instructor.setUser(user);
        instructor.setSpecialization(req.specialization());
        instructor.setPhoneNumber(req.phoneNumber());

        instructorRepository.save(instructor);

        return toResponse(user, instructor);
    }
}
