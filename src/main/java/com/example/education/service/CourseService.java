package com.example.education.service;

import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.material.Material;
import com.example.education.dao.repository.CourseRepository;
import com.example.education.dao.repository.MaterialRepository;
import com.example.education.dao.repository.QuestionRepository;
import com.example.education.dto.request.course.CourseRequest;
import com.example.education.dto.response.PageResponse;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.course.CourseResponse;
import com.example.education.dto.response.material.MaterialResponse;
import com.example.education.dto.response.topic.TopicResponse;
import com.example.education.enums.ResponseCode;
import com.example.education.mapper.CourseMapper;
import com.example.education.mapper.MaterialMapper;
import com.example.education.mapper.TopicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final MaterialRepository materialRepository;
    private final CourseMapper courseMapper;
    private final QuestionRepository questionRepository;

    public SuccessResponse<CourseResponse> getCourseById(Long id) {
        Course course = courseRepository.findCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found..."));

        // Course içindəki Topic-ləri və onların Material-larını mapper-lə çeviririk
        CourseResponse courseResponse = CourseMapper.INSTANCE.entityToResponse(course);

        // Hər topic üçün materialları mapper-lə çeviririk
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

        // Topic-ləri courseResponse-ə əlavə edirik
        courseResponse.setTopics(topicResponses);

        return SuccessResponse.createSuccessResponse(courseResponse, ResponseCode.SUCCESS);
    }


    public SuccessResponse<CourseResponse> createCourse(CourseRequest courseRequest){
        Course course=CourseMapper.INSTANCE.requestToEntity(courseRequest);
        courseRepository.save(course);
        return SuccessResponse.createSuccessResponse(CourseMapper.INSTANCE.entityToResponse(course), ResponseCode.SUCCESS);
    }
    public SuccessResponse<PageResponse<CourseResponse>> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Course> courses = courseRepository.findAll(pageable);

        List<CourseResponse> courseResponses = courses
                .getContent()
                .stream()
                .map(courseMapper::entityToResponse)
                .collect(Collectors.toList());

        return SuccessResponse.createSuccessResponse(
                new PageResponse<>(
                        courseResponses,
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
}