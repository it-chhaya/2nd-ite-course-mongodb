package dev.chanchhaya.course.features.course;

import dev.chanchhaya.course.base.FilterableRepository;
import dev.chanchhaya.course.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String>, FilterableRepository<Course> {

    @Query("{price:  {$lt: ?0}}")
    List<Course> findCoursesByPrice(BigDecimal price);

    @Query("{title:  {$regex: ?0, $options:  'i'}}")
    List<Course> filterCoursesByTitle(String title);

    @Query(value = "{title: {$regex: ?0, $options: 'i'}}", fields = "{title: 1}")
    List<Course> findCoursesTitle(String title);

    Optional<Course> findByIdAndIsDraftedFalse(String id);

    Optional<Course> findBySlugAndIsDraftedFalse(String slug);

    boolean existsBySlug(String slug);

    int countByCategoryName(String categoryName);

    Page<Course> findByIsPaidFalseAndIsDraftedFalse(Pageable pageable);

    Page<Course> findByIsDraftedFalseAndTitleIgnoreCaseContainingOrderByCreatedAtDesc(String title, Pageable pageable);

    Page<Course> findByDiscountGreaterThanAndIsDraftedFalse(Integer discount, Pageable pageable);

    Page<Course> findByIsDraftedTrue(Pageable pageable);

    Page<Course> findByIsDraftedFalse(Pageable pageable);

    List<Course> findByInstructorUsername(String instructorUsername);
}
