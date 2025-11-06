package com.example.education.mapper;

import com.example.education.dao.entity.discussion.Discussion;
import com.example.education.dto.response.discussion.DiscussionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiscussionMapper {

    @Mapping(target = "instructor",
            expression = "java(d.getInstructor()!=null && d.getInstructor().getUser()!=null ? " +
                    "(d.getInstructor().getUser().getFirstName() + \" \" + d.getInstructor().getUser().getLastName()).trim() : \"\")")
    DiscussionResponse entityToResponse(Discussion d);
}
