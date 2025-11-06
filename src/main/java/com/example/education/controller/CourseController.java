package com.example.education.controller;

import com.example.education.dto.request.course.CourseRequest;
import com.example.education.dto.request.discussion.DiscussionCreateRequest;
import com.example.education.dto.request.post.PostCreateRequest;
import com.example.education.dto.response.PageResponse;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.course.CourseDiscussionResponse;
import com.example.education.dto.response.course.CourseResponse;
import com.example.education.dto.response.discussion.DiscussionResponse;
import com.example.education.dto.response.post.PostResponse;
import com.example.education.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/course")
public class CourseController {
    private final CourseService courseService;
//    private final S3Client s3Client;
//    @Value("${aws.s3.bucket-name}")
//    private String bucketName;

    @GetMapping("/{id}")
    public SuccessResponse<CourseResponse> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }
    @GetMapping
    public SuccessResponse<PageResponse<CourseResponse>> getAllCourses(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size) {
        return courseService.getAllCourses(page,size);
    }

    // 2) Kursa yeni discussion yarat
    @PostMapping("/{courseId}/discussions")
    @ResponseStatus(HttpStatus.CREATED)
    public DiscussionResponse createDiscussion(
            @PathVariable Long courseId,
            @Valid @RequestBody DiscussionCreateRequest req) {
        return courseService.addDiscussion(courseId, req);
    }


    // 3) Discussion-a post əlavə et
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

//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            s3Client.putObject(
//                    PutObjectRequest.builder()
//                            .bucket(bucketName)
//                            .key(file.getOriginalFilename())
//                            .build(),
//                    RequestBody.fromBytes(file.getBytes())
//            );
//            return ResponseEntity.ok("File uploaded: " + file.getOriginalFilename());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Upload failed");
//        }
//    }

}
