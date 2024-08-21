package dev.chanchhaya.course.features.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VideoDto(@NotNull
                       @JsonInclude(JsonInclude.Include.NON_NULL)
                       Integer sectionOrderNo,
                       String lectureNo,
                       @NotNull
                       Integer orderNo,
                       @NotBlank
                       String title,
                       @NotBlank
                       String fileName) {

}
