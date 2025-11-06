package com.example.education.dto.response.post;

import com.example.education.dto.response.student.StudentMiniResponse;

import java.time.Instant;
import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        StudentMiniResponse student,
        String content,
        LocalDateTime createdAt) {
}
