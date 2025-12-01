package com.example.education.controller;

import com.example.education.dto.request.UserRequest;
import com.example.education.dto.request.course.CourseRequest;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.course.CourseSimpleResponse;
import com.example.education.dto.response.user.UserResponse;
import com.example.education.enums.RoleName;
import com.example.education.service.CourseService;
import com.example.education.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin-course")
public class CourseAdminController {
    private final UserService userService;
    private final CourseService courseService;

    @PostMapping("/sign-up")
    public SuccessResponse<UserResponse> addUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PostMapping("/course")
    public SuccessResponse<CourseSimpleResponse> createCourse(@RequestBody CourseRequest courseRequest) {
        return courseService.createCourse(courseRequest);
    }
}
