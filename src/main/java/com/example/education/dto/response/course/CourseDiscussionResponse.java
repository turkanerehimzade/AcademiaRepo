package com.example.education.dto.response.course;

import com.example.education.dto.response.discussion.DiscussionResponse;
import com.example.education.dto.response.topic.TopicResponse;

import java.util.List;

public record CourseDiscussionResponse(
         Long id,
         String title,
         String description,
         List<DiscussionResponse> discussion
) {
}
