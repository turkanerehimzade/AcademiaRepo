package com.example.education.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostCreateRequest(
        @NotNull Long studentId,
        @NotBlank String content
) {
}
