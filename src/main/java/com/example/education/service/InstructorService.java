package com.example.education.service;

import com.example.education.dao.entity.User;
import com.example.education.dao.entity.instructor.Instructor;
import com.example.education.dao.repository.InstructorRepository;
import com.example.education.dao.repository.UserRepository;
import com.example.education.dto.request.instructor.InstructorCreateRequest;
import com.example.education.dto.response.PageResponse;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.instructor.InstructorMiniResponse;
import com.example.education.dto.response.instructor.InstructorResponse;
import com.example.education.enums.ResponseCode;
import com.example.education.enums.RoleName;
import com.example.education.mapper.InstructorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final InstructorMapper instructorMapper;


    @Transactional(readOnly = true)
    public SuccessResponse<PageResponse<InstructorMiniResponse>> getListInstructor(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Instructor> instructorPage = instructorRepository.findAll(pageable);

        List<InstructorMiniResponse> responses = instructorPage.getContent().stream()
                .map(instructorMapper::toMini)
                .toList();

        PageResponse<InstructorMiniResponse> pageResponse = new PageResponse<>(
                responses,
                instructorPage.getNumber(),
                instructorPage.getSize(),
                instructorPage.getTotalElements(),
                instructorPage.getTotalPages(),
                instructorPage.isFirst(),
                instructorPage.isLast()
        );

        return SuccessResponse.createSuccessResponse(pageResponse, ResponseCode.SUCCESS);
    }

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
