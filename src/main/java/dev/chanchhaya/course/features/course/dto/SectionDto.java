package dev.chanchhaya.course.features.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SectionDto(@NotBlank
                         String title,
                         @NotNull
                         Integer orderNo,
                         @JsonInclude(JsonInclude.Include.NON_NULL)
                         List<VideoDto> videos) {
}
