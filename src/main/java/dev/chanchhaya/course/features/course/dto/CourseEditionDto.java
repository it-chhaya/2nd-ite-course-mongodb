package dev.chanchhaya.course.features.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.bson.types.Decimal128;

import java.math.BigDecimal;

public record CourseEditionDto(@NotBlank
                               String title,
                               String slug,
                               @NotBlank
                               String description,
                               @NotNull
                               @Positive
                               BigDecimal price,
                               @NotNull
                               @Positive
                               Integer discount,
                               @NotBlank
                               String categoryName,
                               @NotNull
                               Boolean isPaid,
                               @NotNull
                               Boolean isDrafted) {
}
