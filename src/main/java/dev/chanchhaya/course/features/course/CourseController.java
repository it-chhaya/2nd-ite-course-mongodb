package dev.chanchhaya.course.features.course;

import dev.chanchhaya.course.base.BasedStatus;
import dev.chanchhaya.course.base.Part;
import dev.chanchhaya.course.features.course.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;


    @GetMapping("/instructor/{instructorUsername}")
    List<CourseResponse> findByCoursesByInstructorUsername(@PathVariable String instructorUsername) {

        return courseService.findCourseByInstructorUsername(instructorUsername);
    }


    @GetMapping("/slug/{slug}")
    Object findBySlug(@PathVariable String slug,
                      @RequestParam(required = false, defaultValue = "SNIPPET") Part part) {

        return courseService.findBySlug(slug, part);
    }


    @GetMapping("/{id}")
    Object findById(@PathVariable String id,
                    @RequestParam(required = false, defaultValue = "SNIPPET") Part part) {

        return courseService.findById(id, part);
    }


    @GetMapping
    Page<?> findList(@RequestParam(required = false, defaultValue = "SNIPPET") Part part,
                     @RequestParam(defaultValue = "0") int page,
                     @RequestParam(defaultValue = "20") int size) {

        return courseService.findList(part, page, size);
    }


    @GetMapping("/free")
    Page<?> findFreeCourses(
            @RequestParam(required = false, defaultValue = "SNIPPET") Part part,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return courseService.findFreeCourses(part, page, size);
    }


    /*@GetMapping("/promotions")
    Page<?> findPromotionCourses(
            @RequestParam(required = false, defaultValue = "SNIPPET") Part part,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return courseService.findPromotionCourses(part, page, size);
    }*/


    @GetMapping("/public")
    Page<?> findPublicCourses(
            @RequestParam(required = false, defaultValue = "SNIPPET") Part part,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return courseService.findPublicCourses(part, page, size);
    }

    @GetMapping("/private")
    Page<?> findPrivateCourses(
            @RequestParam(required = false, defaultValue = "SNIPPET") Part part,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return courseService.findPrivateCourses(part, page, size);
    }


    @GetMapping("/filters")
    Page<?> filterCourseByTitles(
            @RequestParam String title,
            @RequestParam(required = false, defaultValue = "SNIPPET") Part part,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return courseService.filterCoursesByTitle(title, part, page, size);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/visibilities")
    void editVisibilityStatusById(@PathVariable String id, @Valid @RequestBody BasedStatus basedStatus) {
        courseService.editVisibilityStatusById(id, basedStatus);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/is-paid")
    void editIsPaidStatusById(@PathVariable String id, @Valid @RequestBody BasedStatus basedStatus) {
        courseService.editIsPaidStatusById(id, basedStatus);
    }


    @PutMapping("/{id}/thumbnail")
    Map<String, String> editThumbnailById(@PathVariable String id, @Valid @RequestBody CourseThumbnailDto thumbnailDto) {

        String thumbnail = courseService.editThumbnailById(id, thumbnailDto);
        return Map.of("thumbnail", thumbnail);
    }


    @PutMapping("/{id}")
    Object editById(@PathVariable String id, @Valid @RequestBody CourseEditionDto editionDto) {

        courseService.editById(id, editionDto);
        return courseService.findById(id, Part.SNIPPET);
    }


    @PostMapping("/filter")
    Page<?> filter(@RequestBody FilterDto filter,
                   @RequestParam(required = false, defaultValue = "SNIPPET") Part part,
                   @RequestParam(defaultValue = "0") int page,
                   @RequestParam(defaultValue = "20") int size) {

        return courseService.filter(filter, part, page, size);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Object createNew(@Valid @RequestBody CourseCreationRequest creationDto) {

        String id = courseService.createNew(creationDto);
        return courseService.findById(id, Part.SNIPPET);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void removeById(@PathVariable String id) {
        courseService.removeById(id);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/disable")
    void disable(@PathVariable String id) {
        courseService.disable(id);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/enable")
    void enable(@PathVariable String id) {
        courseService.enable(id);
    }


    /*@ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/settings")
    void editSettings(@PathVariable String id, @Valid @RequestBody CourseSettingsRequest courseSettingsDto) {
        courseService.editSettingsById(id, courseSettingsDto);
    }*/


    // ------------------------ START COURSE SECTION ------------------------ //

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{courseId}/sections")
    SectionDto createCourseSection(@PathVariable String courseId, @Valid @RequestBody SectionDto sectionDto) {

        return courseService.createCourseSection(courseId, sectionDto);
    }

    // ------------------------ END COURSE SECTION ------------------------ //


    // ------------------------ START COURSE VIDEO ------------------------ //

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{courseId}/videos")
    VideoDto createCourseVideo(@PathVariable String courseId,
                               @Valid @RequestBody VideoDto videoDto) {

        return courseService.createCourseVideo(courseId, videoDto);
    }


    @PutMapping("/{courseId}/videos")
    VideoEditionDto editCourseVideo(@PathVariable String courseId,
                                    @Valid @RequestBody VideoEditionDto videoEditionDto) {

        return courseService.editCourseVideo(courseId, videoEditionDto);
    }


}