package com.example.education.dto.response.discussion;

import com.example.education.dto.response.post.PostResponse;
import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public record DiscussionResponse(
        Long id,
        String topic,
        String instructor,   // "Müəllim X"
        LocalDateTime createdAt,
        List<PostResponse> posts

) {
}
