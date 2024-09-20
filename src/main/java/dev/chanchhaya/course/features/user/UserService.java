package dev.chanchhaya.course.features.user;

import dev.chanchhaya.course.features.user.dto.FavoriteCourseResponse;

import java.util.List;

public interface UserService {

    void addFavoriteCourse(String userId, String courseId);

    void removeFavoriteCourse(String userId, String courseId);

    List<FavoriteCourseResponse> getFavoriteCourses(String userId);
}
