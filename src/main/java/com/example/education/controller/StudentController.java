package com.example.education.controller;

import com.example.education.dto.request.student.StudentAttachRequest;
import com.example.education.dto.request.student.StudentCreateRequest;
import com.example.education.dto.response.PageResponse;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.student.StudentGradebookResponse;
import com.example.education.dto.response.student.StudentMiniResponse;
import com.example.education.dto.response.student.StudentResponse;
import com.example.education.service.GradebookService;
import com.example.education.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/student")
public class StudentController {
     private final StudentService studentService;
     private final GradebookService gradebookService;

     @PostMapping
     public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentCreateRequest request) {
          return ResponseEntity.ok(studentService.createStudentWithUser(request));
     }

     @PostMapping("/attach")
     public ResponseEntity<StudentResponse> attach(@Valid @RequestBody StudentAttachRequest request) {
          return ResponseEntity.ok(studentService.attachStudentToExistingUser(request));
     }

     @GetMapping("/{studentId}/course/gradebook")
     public ResponseEntity<SuccessResponse<StudentGradebookResponse>> getStudent(@PathVariable Long studentId) {
          return ResponseEntity.ok(gradebookService.getStudentGradebook(studentId));
     }

     @GetMapping()
     public SuccessResponse<PageResponse<StudentMiniResponse>> getAllStudents(
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size
     ) {
          return studentService.getAllStudents(page, size);
     }

}
