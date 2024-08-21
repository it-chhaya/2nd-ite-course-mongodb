package dev.chanchhaya.course.features.category.dto;

import lombok.Builder;

@Builder
public record PopularCategoryResponse(String icon,
                                      String name,
                                      Integer totalCourses) {
}
