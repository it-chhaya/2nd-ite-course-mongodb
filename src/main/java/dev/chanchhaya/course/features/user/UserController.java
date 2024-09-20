package dev.chanchhaya.course.features.user;

import dev.chanchhaya.course.features.user.dto.FavoriteCourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/{userId}/favorites/{courseId}")
    public void addFavoriteCourse(@PathVariable String userId, @PathVariable String courseId) {
        userService.addFavoriteCourse(userId, courseId);
    }


    @DeleteMapping("/{userId}/favorites/{courseId}")
    public void removeFavoriteCourse(@PathVariable String userId, @PathVariable String courseId) {
        userService.removeFavoriteCourse(userId, courseId);
    }


    @GetMapping("/{userId}/favorites")
    public List<FavoriteCourseResponse> getFavoriteCourses(@PathVariable String userId) {
        return userService.getFavoriteCourses(userId);
    }

}
