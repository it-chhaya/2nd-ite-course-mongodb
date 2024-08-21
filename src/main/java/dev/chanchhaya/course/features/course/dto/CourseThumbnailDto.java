package dev.chanchhaya.course.features.course.dto;

import jakarta.validation.constraints.NotBlank;

public record CourseThumbnailDto(@NotBlank
                                 String thumbnail) {
}
