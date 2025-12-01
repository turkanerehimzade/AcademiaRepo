package com.example.education.controller;

import com.example.education.dto.request.course.CourseRequest;
import com.example.education.dto.request.instructor.InstructorCreateRequest;
import com.example.education.dto.request.interview.InterviewRequest;
import com.example.education.dto.request.material.MaterialRequest;
import com.example.education.dto.request.question.QuestionRequest;
import com.example.education.dto.request.topic.TopicRequest;
import com.example.education.dto.response.PageResponse;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.course.CourseSimpleResponse;
import com.example.education.dto.response.instructor.InstructorMiniResponse;
import com.example.education.dto.response.instructor.InstructorResponse;
import com.example.education.dto.response.material.MaterialResponse;
import com.example.education.dto.response.question.QuestionResponse;
import com.example.education.dto.response.topic.TopicResponse;
import com.example.education.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/instructor")
public class InstructorController {
    private final InstructorService instructorService;
    private final InterviewService interviewService;
    private final CourseService courseService;
    public final MaterialService materialService;
    private final QuestionService questionService;
    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<InstructorResponse> createInstructor(@Valid @RequestBody InstructorCreateRequest request) {
        return ResponseEntity.ok(instructorService.createInstructorWithUser(request));
    }

    @PostMapping("/question")
    public SuccessResponse<QuestionResponse> addQuestion(@RequestBody QuestionRequest questionRequest) {
        return questionService.addQuestion(questionRequest);
    }

    @PostMapping("/material")
    public SuccessResponse<MaterialResponse> addMaterial(@RequestBody MaterialRequest request) {
        return materialService.addMaterial(request);
    }

    @PostMapping("/topic")
    public SuccessResponse<TopicResponse> addTopic(@RequestBody TopicRequest request) {
        return topicService.addTopic(request);

    }

    @PostMapping("/interview-question")
    public SuccessResponse<String> createInterviewQuestion(InterviewRequest interviewRequest) {
        return interviewService.createInterviewQuestion(interviewRequest);
    }

    @GetMapping()
    public SuccessResponse<PageResponse<InstructorMiniResponse>> getListInstructor(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return instructorService.getListInstructor(page, size);
    }

}
