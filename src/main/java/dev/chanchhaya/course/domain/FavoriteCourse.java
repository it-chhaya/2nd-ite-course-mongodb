package dev.chanchhaya.course.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FavoriteCourse {
    private String courseId;
    private String title;
    private String instructor;
    private LocalDateTime addedAt;
}
