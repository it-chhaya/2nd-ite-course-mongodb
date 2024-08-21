package dev.chanchhaya.course.features.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CourseCreationRequest(@NotBlank
                                    String title,
                                    String slug,
                                    @NotBlank
                                    String description,
                                    @NotBlank
                                    String thumbnail,
                                    @NotNull
                                    @Positive
                                    BigDecimal price,
                                    String content,
                                    @NotBlank
                                    String categoryName) {
}
