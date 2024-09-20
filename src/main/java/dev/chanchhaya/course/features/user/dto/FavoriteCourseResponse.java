package dev.chanchhaya.course.features.user.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FavoriteCourseResponse(
        String courseId,
        String title,
        String instructor,
        LocalDateTime addedAt
) {
}
