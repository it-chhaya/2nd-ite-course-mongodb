package dev.chanchhaya.course.features.course;

import dev.chanchhaya.course.base.FilterableRepository;
import dev.chanchhaya.course.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String>, FilterableRepository<Course> {

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
