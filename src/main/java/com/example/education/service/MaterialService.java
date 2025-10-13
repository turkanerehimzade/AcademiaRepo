package com.example.education.service;

import com.example.education.dao.entity.course.Course;
import com.example.education.dao.entity.material.Material;
import com.example.education.dao.entity.topic.Topic;
import com.example.education.dao.repository.CourseRepository;
import com.example.education.dao.repository.MaterialRepository;
import com.example.education.dao.repository.TopicRepository;
import com.example.education.dto.request.material.MaterialRequest;
import com.example.education.dto.response.base.SuccessResponse;
import com.example.education.dto.response.material.MaterialResponse;
import com.example.education.dto.response.question.QuestionResponse;
import com.example.education.enums.ResponseCode;
import com.example.education.mapper.MaterialMapper;
import com.example.education.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final CourseRepository courseRepository;
    private final TopicRepository topicRepository;
    public SuccessResponse<MaterialResponse> addMaterial(MaterialRequest materialRequest) {
        Topic topic = topicRepository.findById(materialRequest.getTopicId()).orElseThrow(() -> new RuntimeException("Topic Not Found"));
        Material material = MaterialMapper.INSTANCE.entityToRequest(materialRequest);
        material.setTopic(topic);
        materialRepository.save(material);
        MaterialResponse materialResponse = MaterialMapper.INSTANCE.entityToResponse(material);
        return SuccessResponse.createSuccessResponse(materialResponse, ResponseCode.SUCCESS);
    }
//    public SuccessResponse<List<MaterialResponse>> getAllMaterials(Long courseId) {
//        List<Material> materials = materialRepository.findByCourseId(courseId);
//        List<MaterialResponse> materialResponses = materials.stream()
//                .map(MaterialMapper.INSTANCE::entityToResponse)
//                .collect(Collectors.toList());
//        return SuccessResponse.createSuccessResponse(materialResponses, ResponseCode.SUCCESS);
//
//    }
}
