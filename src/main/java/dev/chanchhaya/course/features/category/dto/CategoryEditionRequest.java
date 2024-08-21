package dev.chanchhaya.course.features.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryEditionRequest(@NotBlank String name) {
}
