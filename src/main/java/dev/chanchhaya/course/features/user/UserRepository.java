package dev.chanchhaya.course.features.user;

import dev.chanchhaya.course.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("{ 'id': ?0, 'favoriteCourses.courseId': { $ne: ?1 } }")
    @Update("{ $push: { 'favoriteCourses': { 'courseId': ?1, 'title': ?2, 'instructor': ?3, 'addedAt': ?4 } } }")
    void addFavoriteCourse(String userId, String courseId, String title, String instructor, LocalDateTime addedAt);

    @Query("{ 'id': ?0 }")
    @Update("{ $pull: { 'favoriteCourses': { 'courseId': ?1 } } }")
    void removeFavoriteCourse(String userId, String courseId);

}
