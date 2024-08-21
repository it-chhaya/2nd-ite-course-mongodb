package dev.chanchhaya.course.features.course;

import dev.chanchhaya.course.base.BasedStatus;
import dev.chanchhaya.course.base.Part;
import dev.chanchhaya.course.features.course.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    Object findBySlug(String slug, Part part);

    Object findById(String id, Part part);

    Page<?> findList(Part part, int page, int size);

    Page<?> findFreeCourses(Part part, int page, int size);

    Page<?> findPromotionCourses(Part part, int page, int size);

    Page<?> findPublicCourses(Part part, int page, int size);

    Page<?> findPrivateCourses(Part part, int page, int size);

    Page<?> filterCoursesByTitle(String title, Part part, int page, int size);

    void editVisibilityStatusById(String id, BasedStatus basedStatus);

    void editIsPaidStatusById(String id, BasedStatus basedStatus);

    String editThumbnailById(String id, CourseThumbnailDto thumbnailDto);

    void editById(String id, CourseEditionDto editionDto);

    Page<?> filter(FilterDto filter, Part part, int page, int size);

    String createNew(CourseCreationRequest creationDto);

    void removeById(String id);

    void disable(String id);

    void enable(String id);

    void editSettingsById(String id, CourseSettingsRequest courseSettingsDto);


    // ------------------------ START COURSE SECTION ------------------------ //


    SectionDto createCourseSection(String courseId, SectionDto sectionDto);


    // ------------------------ START COURSE VIDEO ------------------------ //


    VideoDto createCourseVideo(String courseId, VideoDto videoDto);

    VideoEditionDto editCourseVideo(String courseId, VideoEditionDto videoEditionDto);

    List<CourseResponse> findCourseByInstructorUsername(String instructorUsername);
}
