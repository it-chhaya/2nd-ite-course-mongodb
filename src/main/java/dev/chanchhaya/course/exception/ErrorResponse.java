package dev.chanchhaya.course.exception;

public record ErrorResponse<T>(T error) {
}
