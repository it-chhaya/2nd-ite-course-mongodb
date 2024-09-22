package dev.chanchhaya.course.features.user.dto;

public record UserResponse(
        String id,
        String email,
        String biography,
        String gender,
        String profile
) {
}
