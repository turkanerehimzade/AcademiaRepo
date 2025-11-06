package com.example.education.dto.request.discussion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DiscussionCreateRequest(
        @NotBlank String topic,
        @NotNull Long instructorId
) {
}
