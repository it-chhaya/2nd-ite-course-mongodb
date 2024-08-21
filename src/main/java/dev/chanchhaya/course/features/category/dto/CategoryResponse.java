package dev.chanchhaya.course.features.category.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(String id,
                               String uuid,
                               String name,
                               String icon) {
}
