package com.example.education.dto.response.course;

import com.example.education.dto.response.discussion.DiscussionResponse;

import java.util.List;

public record CourseDiscussionResponse(
         Long id,
         String title,
         String description,
         String groupName,
         List<DiscussionResponse> discussion
) {
}
