package dev.chanchhaya.course.features.course.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CourseSettingsRequest(@NotNull Boolean isPaid,
                                    @NotNull
                                    @Positive
                                    @Max(100)
                                    Integer discount,
                                    @NotNull Boolean isDrafted) {
}
