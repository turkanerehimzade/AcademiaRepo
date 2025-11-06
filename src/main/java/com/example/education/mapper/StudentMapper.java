package com.example.education.mapper;

import com.example.education.dao.entity.student.Student;
import com.example.education.dto.response.student.StudentMiniResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", expression = "java(fullName(student))")
    StudentMiniResponse toMini(Student student);

    default String fullName(Student s) {
        if (s == null || s.getUser() == null) return null;
        String fn = s.getUser().getFirstName();
        String ln = s.getUser().getLastName();
        String res = ((fn == null ? "" : fn) + " " + (ln == null ? "" : ln)).trim();
        return res.isEmpty() ? null : res;
    }
}
