package com.example.education.mapper;

import com.example.education.dao.entity.course.Course;
import com.example.education.dto.request.course.CourseRequest;
import com.example.education.dto.response.course.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
    CourseResponse entityToResponse(Course course);
    Course requestToEntity(CourseRequest courseRequest);


}
