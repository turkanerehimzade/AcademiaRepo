package com.example.education.service;

import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.material.Material;
import com.example.education.dao.entity.topic.Topic;
import com.example.education.dao.repository.CourseRepository;
import com.example.education.dao.repository.MaterialRepository;
import com.example.education.dao.repository.TopicRepository;
import com.example.education.dto.request.material.MaterialRequest;
import com.example.education.dto.request.topic.TopicRequest;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.material.MaterialResponse;
import com.example.education.dto.response.topic.TopicResponse;
import com.example.education.enums.ResponseCode;
import com.example.education.mapper.MaterialMapper;
import com.example.education.mapper.TopicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    public SuccessResponse<TopicResponse> addTopic(TopicRequest topicRequest) {
        Course course = courseRepository.findById(topicRequest.getCourseId()).orElseThrow(() -> new RuntimeException("Course Not Found"));
        Topic topic = TopicMapper.INSTANCE.entityToRequest(topicRequest);
        topic.setCourse(course);
        topicRepository.save(topic);
        TopicResponse topicResponse=TopicMapper.INSTANCE.entityToResponse(topic);
        return SuccessResponse.createSuccessResponse(topicResponse, ResponseCode.SUCCESS);
    }
}
