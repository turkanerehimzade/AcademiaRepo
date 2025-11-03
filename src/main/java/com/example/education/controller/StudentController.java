package com.example.education.controller;

import com.example.education.dto.request.student.StudentAttachRequest;
import com.example.education.dto.request.student.StudentCreateRequest;
import com.example.education.dto.response.student.StudentResponse;
import com.example.education.service.AuthenticationService;
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
     // Ssenari A: Yeni user + student (ADMIN yaradır)
     @PostMapping
     public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentCreateRequest request) {
          return ResponseEntity.ok(studentService.createStudentWithUser(request));
     }

     // Ssenari B: Mövcud user-ə student profili bağla (ADMIN və ya INSTRUCTOR səlahiyyəti)
     @PostMapping("/attach")
     public ResponseEntity<StudentResponse> attach(@Valid @RequestBody StudentAttachRequest request) {
          return ResponseEntity.ok(studentService.attachStudentToExistingUser(request));
     }
}
