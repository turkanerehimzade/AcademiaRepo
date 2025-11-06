package com.example.education.mapper;

import com.example.education.dao.entity.instructor.Instructor;
import com.example.education.dto.response.instructor.InstructorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InstructorMapper {
    InstructorMapper INSTANCE = Mappers.getMapper(InstructorMapper.class);

    @Mapping(target = "fullName", expression = "java(instructor.getUser().getFirstName() + \" \" + instructor.getUser().getLastName())")
    @Mapping(target = "email", source = "user.email")
    InstructorResponse entityToResponse(Instructor instructor);
}
