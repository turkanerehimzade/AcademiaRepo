package com.example.education.controller;

import com.example.education.dto.request.instructor.InstructorCreateRequest;
import com.example.education.dto.request.student.StudentCreateRequest;
import com.example.education.dto.response.instructor.InstructorResponse;
import com.example.education.dto.response.student.StudentResponse;
import com.example.education.service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    @PostMapping
    public ResponseEntity<InstructorResponse> create(@Valid @RequestBody InstructorCreateRequest request) {
        return ResponseEntity.ok(instructorService.createInstructorWithUser(request));
    }

}
