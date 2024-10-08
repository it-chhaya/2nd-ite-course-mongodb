package dev.chanchhaya.course.features.user;

import dev.chanchhaya.course.domain.Course;
import dev.chanchhaya.course.domain.User;
import dev.chanchhaya.course.features.course.CourseRepository;
import dev.chanchhaya.course.features.user.dto.FavoriteCourseResponse;
import dev.chanchhaya.course.features.user.dto.UserResponse;
import dev.chanchhaya.course.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CourseRepository courseRepository;

    @Override
    public void addFavoriteCourse(String userId, String courseId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        userRepository.addFavoriteCourse(userId, course.getId(), course.getTitle(),
                course.getInstructorUsername(), LocalDateTime.now());
    }

    public void removeFavoriteCourse(String userId, String courseId) {
        userRepository.removeFavoriteCourse(userId, courseId);
    }

    @Override
    public List<FavoriteCourseResponse> getFavoriteCourses(String userId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFavoriteCourses()
                .stream()
                .map(userMapper::toFavoriteCourseResponse)
                .toList();
    }


    @Override
    public UserResponse me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) auth.getPrincipal();
        User user = userRepository
                .findByEmail(jwt.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return userMapper.toUserResponse(user);
    }


}
