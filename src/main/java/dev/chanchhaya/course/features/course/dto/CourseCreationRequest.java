package dev.chanchhaya.course.features.course.dto;

import dev.chanchhaya.course.domain.Instructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import org.bson.types.Decimal128;

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
                                    Decimal128 price,
                                    String content,
                                    @NotBlank
                                    String categoryName,
                                    Instructor instructor) {
}
