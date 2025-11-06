package com.example.education.mapper;

import com.example.education.dao.entity.post.Post;
import com.example.education.dto.request.post.PostCreateRequest;
import com.example.education.dto.response.post.PostResponse;
import org.mapstruct.*;

import java.time.*;

@Mapper(componentModel = "spring", uses = { StudentMapper.class })
public interface PostMapper {

    // Request -> Entity (content-dən başqa sahə yoxdursa belə)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "discussion", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "createdAt", ignore = true) // Auditing/PrePersist ilə dolacaqsa
    Post requestToEntity(PostCreateRequest req);

    // Entity -> DTO
//    @Mapping(target = "student", source = "student") // StudentMapper.toMini çağırılacaq
//    @Mapping(target = "createdAt", expression = "java(toLocalDateTime(p.getCreatedAt()))")
    PostResponse entityToResponse(Post p);

    // Əgər entity-də createdAt = Instant-dırsa, çevir:
    default LocalDateTime toLocalDateTime(Object t) {
        if (t == null) return null;
        if (t instanceof LocalDateTime ldt) return ldt;
        if (t instanceof Instant ins) return LocalDateTime.ofInstant(ins, ZoneId.systemDefault());
        return null;
    }
}
