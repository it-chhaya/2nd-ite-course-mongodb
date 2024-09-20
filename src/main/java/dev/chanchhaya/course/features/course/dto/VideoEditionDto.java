package dev.chanchhaya.course.features.course.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VideoEditionDto(@NotNull
                              Integer sectionOrderNo,
                              @NotNull
                              List<VideoDto> videos) {
}