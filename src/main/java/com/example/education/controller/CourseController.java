package com.example.education.controller;

import com.example.education.dto.request.discussion.DiscussionCreateRequest;
import com.example.education.dto.request.post.PostCreateRequest;
import com.example.education.dto.response.PageResponse;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.course.CourseDiscussionResponse;
import com.example.education.dto.response.course.CourseNameResponse;
import com.example.education.dto.response.course.CourseResponse;
import com.example.education.dto.response.course.CourseSimpleResponse;
import com.example.education.dto.response.discussion.DiscussionResponse;
import com.example.education.dto.response.post.PostResponse;
import com.example.education.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{id}")
    public SuccessResponse<CourseResponse> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @GetMapping("/student/{id}")
    public SuccessResponse<List<CourseSimpleResponse>> getCourseByStudentId(@PathVariable Long id) {
        return courseService.getCoursesByStudentId(id);
    }

    @GetMapping
    public SuccessResponse<PageResponse<CourseNameResponse>> getAllCourses(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        return courseService.getAllCourses(page, size);
    }

    @PostMapping("/{courseId}/discussions")
    @ResponseStatus(HttpStatus.CREATED)
    public DiscussionResponse createDiscussion(
            @PathVariable Long courseId,
            @Valid @RequestBody DiscussionCreateRequest req) {
        return courseService.addDiscussion(courseId, req);
    }

    @PostMapping("/discussions/{discussionId}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(
            @PathVariable Long discussionId,
            @Valid @RequestBody PostCreateRequest req) {
        return courseService.addPost(discussionId, req);
    }

    @GetMapping("/{courseId}/discussion")
    public SuccessResponse<List<CourseDiscussionResponse>> getDiscussions(
            @PathVariable Long courseId) {
        return courseService.getDiscussionByCourseId(courseId);
    }
}
