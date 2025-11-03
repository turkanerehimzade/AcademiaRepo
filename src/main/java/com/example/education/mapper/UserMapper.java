package com.example.education.mapper;
import com.example.education.dao.entity.User;
import com.example.education.dto.request.UserRequest;
import com.example.education.dto.response.user.UserResponse;
import com.example.education.dto.response.user.UserSignInResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toEntity(UserRequest userRequest);
    UserResponse toResponse(User user);
    UserSignInResponse toSignInResponse(User user);
}
