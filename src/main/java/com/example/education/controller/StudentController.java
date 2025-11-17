package com.example.education.controller;

import com.example.education.dto.request.student.StudentAttachRequest;
import com.example.education.dto.request.student.StudentCreateRequest;
import com.example.education.dto.response.student.StudentResponse;
import com.example.education.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student")
public class StudentController {
     private final StudentService studentService;

     @PostMapping
     public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentCreateRequest request) {
          return ResponseEntity.ok(studentService.createStudentWithUser(request));
     }

     @PostMapping("/attach")
     public ResponseEntity<StudentResponse> attach(@Valid @RequestBody StudentAttachRequest request) {
          return ResponseEntity.ok(studentService.attachStudentToExistingUser(request));
     }
}
