package com.example.education.controller;

import com.example.education.dto.request.UserRequest;
import com.example.education.dto.request.course.CourseRequest;
import com.example.education.dto.request.interview.InterviewRequest;
import com.example.education.dto.request.material.MaterialRequest;
import com.example.education.dto.request.question.QuestionRequest;
import com.example.education.dto.request.topic.TopicRequest;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.course.CourseResponse;
import com.example.education.dto.response.material.MaterialResponse;
import com.example.education.dto.response.question.QuestionResponse;
import com.example.education.dto.response.topic.TopicResponse;
import com.example.education.dto.response.user.UserResponse;
import com.example.education.enums.RoleName;
import com.example.education.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminController {
    private final UserService userService;
    private final InterviewService interviewService;
    private final CourseService courseService;
    public final MaterialService materialService;
    private final QuestionService questionService;
    private final TopicService topicService;

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

    @PostMapping("/sign-up")
    public SuccessResponse<UserResponse> addUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }
    @PostMapping("/interview-question")
    public SuccessResponse<String> createInterviewQuestion(InterviewRequest interviewRequest) {
        return interviewService.createInterviewQuestion(interviewRequest);
    }
    @PostMapping("/course")
    public SuccessResponse<CourseResponse> createCourse(@RequestBody CourseRequest courseRequest) {
        return courseService.createCourse(courseRequest);
    }
}
