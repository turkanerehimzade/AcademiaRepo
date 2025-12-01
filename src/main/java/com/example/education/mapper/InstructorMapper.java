package com.example.education.mapper;

import com.example.education.dao.entity.instructor.Instructor;
import com.example.education.dto.response.instructor.InstructorMiniResponse;
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
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", expression = "java(fullName(instructor))")
    InstructorMiniResponse toMini(Instructor instructor);

    default String fullName(Instructor instructor) {
        if (instructor.getUser() == null) return null;
        String first = instructor.getUser().getFirstName();
        String last  = instructor.getUser().getLastName();
        return (first != null ? first : "") + " " + (last != null ? last : "");
    }
}
