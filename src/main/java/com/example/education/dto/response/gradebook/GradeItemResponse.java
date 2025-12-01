package com.example.education.dto.response.gradebook;

public record GradeItemResponse(
        Long id,
        String title,
        double score,
        double maxScore,
        double percentage
) {
}
