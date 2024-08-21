package dev.chanchhaya.course.features.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreationRequest(@NotBlank

                                  String name,
                                      @NotBlank
                                  String icon) {
}
