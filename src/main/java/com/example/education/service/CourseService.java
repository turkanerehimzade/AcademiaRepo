package com.example.education.service;

import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.discussion.Discussion;
import com.example.education.dao.entity.instructor.Instructor;
import com.example.education.dao.entity.post.Post;
import com.example.education.dao.entity.student.Student;
import com.example.education.dao.repository.*;
import com.example.education.dto.request.course.CourseRequest;
import com.example.education.dto.request.discussion.DiscussionCreateRequest;
import com.example.education.dto.request.post.PostCreateRequest;
import com.example.education.dto.response.PageResponse;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.course.CourseDiscussionResponse;
import com.example.education.dto.response.course.CourseNameResponse;
import com.example.education.dto.response.course.CourseResponse;
import com.example.education.dto.response.course.CourseSimpleResponse;
import com.example.education.dto.response.discussion.DiscussionResponse;
import com.example.education.dto.response.material.MaterialResponse;
import com.example.education.dto.response.post.PostResponse;
import com.example.education.dto.response.topic.TopicResponse;
import com.example.education.enums.ResponseCode;
import com.example.education.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final MaterialRepository materialRepository;
    private final CourseMapper courseMapper;
    private final QuestionRepository questionRepository;
    private final InstructorRepository instructorRepository;
    private final DiscussionRepository discussionRepository;
    private final StudentRepository studentRepository;
    private final PostRepository postRepository;
    private final DiscussionMapper discussionMapper;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public SuccessResponse<List<CourseSimpleResponse>> getCoursesByStudentId(Long userId) {

        List<Student> students = studentRepository.findAllByUserId(userId);
        if (students.isEmpty()) {
            throw new IllegalArgumentException("Student not found with userId: " + userId);
        }

        List<Course> courses = students.stream()
                .filter(s -> s.getCourses() != null)
                .flatMap(s -> s.getCourses().stream())
                .distinct()
                .toList();

        if (courses.isEmpty()) {
            throw new IllegalArgumentException("No courses found for this student");
        }

        List<CourseSimpleResponse> responses = courses.stream()
                .map(courseMapper::toCourseSimpleResponse)
                .toList();

        return SuccessResponse.createSuccessResponse(responses, ResponseCode.SUCCESS);
    }


    @Transactional(readOnly = true)
    public SuccessResponse<List<CourseDiscussionResponse>> getDiscussionByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        List<Discussion> discussions = discussionRepository.findDeepByCourseId(courseId);

        List<DiscussionResponse> discussionDtos = discussions.stream()
                .map(discussionMapper::entityToResponse)
                .toList();

        CourseDiscussionResponse courseDto = new CourseDiscussionResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getGroupName(),
                discussionDtos
        );
        return SuccessResponse.createSuccessResponse(List.of(courseDto), ResponseCode.SUCCESS);
    }


    @Transactional
    public SuccessResponse<PageResponse<CourseDiscussionResponse>> getAllDiscussion(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Course> coursePage = courseRepository.findAll(pageable);

        List<CourseDiscussionResponse> responses = coursePage.getContent().stream()
                .map(course -> new CourseDiscussionResponse(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription(),
                        course.getGroupName(),
                        course.getDiscussions().stream()
                                .map(discussion -> discussionMapper.entityToResponse(discussion))
                                .toList()
                ))
                .toList();

        PageResponse<CourseDiscussionResponse> pageResponse = new PageResponse<>(
                responses,
                coursePage.getNumber(),
                coursePage.getSize(),
                coursePage.getTotalElements(),
                coursePage.getTotalPages(),
                coursePage.isFirst(),
                coursePage.isLast()
        );

        return SuccessResponse.createSuccessResponse(pageResponse, ResponseCode.SUCCESS);
    }

    public SuccessResponse<CourseResponse> getCourseById(Long id) {
        Course course = courseRepository.findCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found..."));

        CourseResponse courseResponse = courseMapper.entityToResponse(course);

        List<TopicResponse> topicResponses = course.getTopics().stream()
                .map(topic -> {
                    TopicResponse topicResponse = TopicMapper.INSTANCE.entityToResponse(topic);
                    List<MaterialResponse> materialResponses = topic.getMaterials().stream()
                            .map(MaterialMapper.INSTANCE::entityToResponse)
                            .collect(Collectors.toList());
                    topicResponse.setMaterials(materialResponses);
                    return topicResponse;
                })
                .collect(Collectors.toList());

        courseResponse.setTopics(topicResponses);

        return SuccessResponse.createSuccessResponse(courseResponse, ResponseCode.SUCCESS);
    }

    public SuccessResponse<CourseSimpleResponse> createCourse(CourseRequest courseRequest) {
        if (courseRequest.getGroupName() == null || courseRequest.getGroupName().isBlank()) {
            throw new IllegalArgumentException("groupName boş ola bilməz!");
        }

        if (courseRepository.existsByGroupName(courseRequest.getGroupName())) {
            throw new IllegalStateException(
                    "Bu qrupa aid kurs artıq mövcuddur: " + courseRequest.getGroupName()
            );
        }

        Course course = courseMapper.requestToEntity(courseRequest);
        courseRepository.save(course);
        return SuccessResponse.createSuccessResponse(
                courseMapper.toCourseSimpleResponse(course), ResponseCode.SUCCESS);
    }

    public SuccessResponse<PageResponse<CourseNameResponse>> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Course> courses = courseRepository.findAll(pageable);

        List<CourseNameResponse> courseNameResponse = courses.getContent().stream()
                .map(courseMapper::toResponse)
                .collect(Collectors.toList());

        return SuccessResponse.createSuccessResponse(
                new PageResponse<>(
                        courseNameResponse,
                        courses.getNumber(),
                        courses.getSize(),
                        courses.getTotalElements(),
                        courses.getTotalPages(),
                        courses.isFirst(),
                        courses.isLast()
                ),
                ResponseCode.SUCCESS
        );
    }

    @Transactional
    public CourseResponse getCourseDeep(Long id) {
        Course c = courseRepository.findWithAllById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return courseMapper.entityToResponse(c);
    }

    public DiscussionResponse addDiscussion(Long courseId, DiscussionCreateRequest req) {
        Course c = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        Instructor inst = instructorRepository.findById(req.instructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Discussion d = new Discussion();
        d.setCourse(c);
        d.setInstructor(inst);
        d.setTopic(req.topic());

        discussionRepository.save(d);

        return discussionMapper.entityToResponse(d);
    }

    public PostResponse addPost(Long discussionId, PostCreateRequest req) {
        var d = discussionRepository.findById(discussionId)
                .orElseThrow(() -> new RuntimeException("Discussion not found"));
        var s = studentRepository.findById(req.studentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Post p = postMapper.requestToEntity(req);
        p.setDiscussion(d);
        p.setStudent(s);

        postRepository.save(p);
        return postMapper.entityToResponse(p);
    }

}
