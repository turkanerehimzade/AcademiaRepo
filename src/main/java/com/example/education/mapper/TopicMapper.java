package com.example.education.mapper;

import com.example.education.dao.entity.material.Material;
import com.example.education.dao.entity.topic.Topic;
import com.example.education.dto.request.topic.TopicRequest;
import com.example.education.dto.response.material.MaterialResponse;
import com.example.education.dto.response.topic.TopicResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);
    TopicResponse entityToResponse(Topic topic);
    Topic entityToRequest(TopicRequest topicRequest);
}
