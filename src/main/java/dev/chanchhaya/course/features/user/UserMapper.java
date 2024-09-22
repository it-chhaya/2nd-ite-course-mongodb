package dev.chanchhaya.course.features.user;

import dev.chanchhaya.course.domain.FavoriteCourse;
import dev.chanchhaya.course.domain.User;
import dev.chanchhaya.course.features.auth.dto.RegisterRequest;
import dev.chanchhaya.course.features.user.dto.FavoriteCourseResponse;
import dev.chanchhaya.course.features.user.dto.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromRegisterRequest(RegisterRequest registerRequest);

    FavoriteCourseResponse toFavoriteCourseResponse(FavoriteCourse favoriteCourse);

    UserResponse toUserResponse(User user);

}
