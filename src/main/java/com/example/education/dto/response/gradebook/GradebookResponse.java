package com.example.education.dto.response.gradebook;

import java.util.List;

public record GradebookResponse(
        double overall,
        List<GradeItemResponse> grades
) {
}
