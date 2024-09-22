package dev.chanchhaya.course.features.course;

import dev.chanchhaya.course.base.BasedStatus;
import dev.chanchhaya.course.base.FilteringFactory;
import dev.chanchhaya.course.base.Part;
import dev.chanchhaya.course.domain.Course;
import dev.chanchhaya.course.domain.Section;
import dev.chanchhaya.course.domain.Video;
import dev.chanchhaya.course.utils.SecureRandomUtil;
import dev.chanchhaya.course.utils.SlugUtil;
import dev.chanchhaya.course.features.course.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public Object findBySlug(String slug, Part part) {

        Course foundCourse = courseRepository.findBySlugAndIsDraftedFalse(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Course doesn't exist in database!"));

        if (part.equals(Part.SNIPPET)) {
            return courseMapper.toCourseSnippetDto(foundCourse);
        }

        return courseMapper.toCourseDto(foundCourse);
    }

    @Override
    public Object findById(String id, Part part) {

        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with ID = %s doesn't exist in database!", id)));

        if (part.equals(Part.SNIPPET)) {
            return courseMapper.toCourseSnippetDto(foundCourse);
        }

        return courseMapper.toCourseDto(foundCourse);
    }


    @Override
    public Page<?> findList(Part part, int page, int size) {

        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sortByCreatedAt);

        Page<Course> coursePage = courseRepository.findByIsDraftedFalse(pageable);

        if (part.equals(Part.SNIPPET)) {
            return coursePage.map(courseMapper::toCourseSnippetDto);
        }

        return coursePage.map(courseMapper::toCourseDto);
    }

    @Override
    public Page<?> findFreeCourses(Part part, int page, int size) {

        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sortByCreatedAt);

        Page<Course> freeCoursesPage = courseRepository.findByIsPaidFalseAndIsDraftedFalse(pageable);

        log.info("Found {} free courses", freeCoursesPage.getTotalElements());

        if (part.equals(Part.SNIPPET)) {
            return freeCoursesPage.map(courseMapper::toCourseSnippetDto);
        }

        return freeCoursesPage.map(courseMapper::toCourseDto);
    }

    @Override
    public Page<?> findPromotionCourses(Part part, int page, int size) {

        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sortByCreatedAt);

        Page<Course> promotionCoursesPage = courseRepository.findByDiscountGreaterThanAndIsDraftedFalse(0, pageable);

        if (part.equals(Part.SNIPPET)) {
            return promotionCoursesPage.map(courseMapper::toCourseSnippetDto);
        }

        return promotionCoursesPage.map(courseMapper::toCourseDto);
    }

    @Override
    public Page<?> findPublicCourses(Part part, int page, int size) {

        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sortByCreatedAt);

        Page<Course> draftedCoursesPage = courseRepository.findByIsDraftedFalse(pageable);

        log.info("Found {} public courses", draftedCoursesPage.getTotalElements());

        if (part.equals(Part.SNIPPET)) {
            return draftedCoursesPage.map(courseMapper::toCourseSnippetDto);
        }

        return draftedCoursesPage.map(courseMapper::toCourseDto);
    }

    @Override
    public Page<?> findPrivateCourses(Part part, int page, int size) {

        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sortByCreatedAt);

        Page<Course> draftedCoursesPage = courseRepository.findByIsDraftedTrue(pageable);

        log.info("Found {} private courses", draftedCoursesPage.getTotalElements());

        if (part.equals(Part.SNIPPET)) {
            return draftedCoursesPage.map(courseMapper::toCourseSnippetDto);
        }

        return draftedCoursesPage.map(courseMapper::toCourseDto);
    }


    @Override
    public Page<?> filterCoursesByTitle(String title, Part part, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Course> coursePage = courseRepository.findByIsDraftedFalseAndTitleIgnoreCaseContainingOrderByCreatedAtDesc(title, pageable);

        if (part.equals(Part.SNIPPET)) {
            return coursePage.map(courseMapper::toCourseSnippetDto);
        }

        return coursePage.map(courseMapper::toCourseDto);
    }


    @Override
    public void editVisibilityStatusById(String id, BasedStatus basedStatus) {

        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with ID = %s doesn't exist in database!", id)));

        if (foundCourse.getIsDrafted().equals(basedStatus.status())) {
            log.info("Someone try to edit course isDrafted status with the same status!");
            return;
        }

        foundCourse.setIsDrafted(basedStatus.status());
        courseRepository.save(foundCourse);

    }

    @Override
    public void editIsPaidStatusById(String id, BasedStatus basedStatus) {

        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with ID = %s doesn't exist in database!", id)));

        if (foundCourse.getIsPaid().equals(basedStatus.status())) {
            log.info("Someone try to edit course isPaid status with the same status!");
            return;
        }

        foundCourse.setIsPaid(basedStatus.status());
        courseRepository.save(foundCourse);
    }

    @Override
    public String editThumbnailById(String id, CourseThumbnailDto thumbnailDto) {

        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with ID = %s doesn't exist in database!", id)));

        foundCourse.setThumbnail(thumbnailDto.thumbnail());
        foundCourse = courseRepository.save(foundCourse);

        return foundCourse.getThumbnail();
    }

    @Override
    public void editById(String id, CourseEditionDto editionDto) {

        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with ID = %s doesn't exist in database!", id)));

        // Partially mapped
        courseMapper.mapFromEditionDto(editionDto, foundCourse);

        courseRepository.save(foundCourse);
    }

    @Override
    public Page<?> filter(FilterDto filter, Part part, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Course> filteredCourses = courseRepository.findAllWithFilter(Course.class,
                FilteringFactory.parseFromParams(filter.filter(), Course.class), pageable);

        if (part.equals(Part.SNIPPET)) {
            return filteredCourses.map(courseMapper::toCourseSnippetDto);
        }

        return filteredCourses.map(courseMapper::toCourseDto);
    }

    @Override
    public String createNew(CourseCreationRequest creationDto) {

        Course course = courseMapper.fromCreationDto(creationDto);

        if (creationDto.slug() == null) {
            String initSlug = SlugUtil.init(creationDto.title());
            course.setSlug(initSlug);
        }

        if (courseRepository.existsBySlug(course.getSlug())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slug has already existed!");
        }

        course.setUuid(UUID.randomUUID().toString());
        course.setCode(SecureRandomUtil.init(16));
        course.setInstructorUsername(creationDto.instructorUsername()); // Need to extract form security context
        course.setSections(new ArrayList<>());
        course.setCreatedAt(LocalDateTime.now());
        course.setUpdatedAt(LocalDateTime.now());
        course.setDiscount(0);
        course.setIsPaid(false);
        course.setIsDrafted(true);
        course.setIsDeleted(false);

        course = courseRepository.save(course);

        return course.getId();
    }

    @Override
    public void removeById(String id) {

        if (courseRepository.existsById(id))
            courseRepository.deleteById(id);

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Course with ID = %s doesn't exist in database!", id));
    }

    @Override
    public void disable(String id) {


    }

    // this method use to public course isDrafted false
    @Override
    public void enable(String id) {
        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with ID = %s doesn't exist in database!", id)));
        //foundCourse.setIsDeleted(false);
        foundCourse.setIsDrafted(false);
        courseRepository.save(foundCourse);
    }

    @Override
    public void editSettingsById(String id, CourseSettingsRequest courseSettingsDto) {

        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with ID = %s doesn't exist in database!", id)));

        foundCourse.setIsPaid(courseSettingsDto.isPaid());
        foundCourse.setDiscount(courseSettingsDto.discount());
        foundCourse.setIsDrafted(courseSettingsDto.isDrafted());
        courseRepository.save(foundCourse);
    }

    // ------------------------ START COURSE SECTION ------------------------ //


    @Override
    public SectionDto createCourseSection(String courseId, SectionDto sectionDto) {

        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with ID = %s doesn't exist in database!", courseId)));

        Section section = new Section();
        section.setTitle(sectionDto.title());
        section.setOrderNo(sectionDto.orderNo());

        foundCourse.getSections().add(section);

        courseRepository.save(foundCourse);

        return sectionDto;
    }


    // ------------------------ START COURSE VIDEO ------------------------ //


    @Override
    public VideoDto createCourseVideo(String courseId, VideoDto videoDto) {

        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with ID = %s doesn't exist in database!", courseId)));

        List<Section> sections = foundCourse.getSections();

        if (sections.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course doesn't existed section(s)");
        }

        // Init video object for storing in DB
        Video video = new Video();

        List<Section> savedSections = sections.stream()
                .peek(section -> {
                    if (section.getOrderNo().equals(videoDto.sectionOrderNo())) {
                        if (section.getVideos() == null) {
                            section.setVideos(new ArrayList<>());
                        }
                        video.setLectureNo(SecureRandomUtil.init(8));
                        video.setOrderNo(videoDto.orderNo());
                        video.setTitle(videoDto.title());
                        video.setFileName(videoDto.fileName());
                        section.getVideos().add(video);
                    }
                })
                .toList();

        foundCourse.setSections(savedSections);

        courseRepository.save(foundCourse);

        return courseMapper.toVideoDto(video);
    }

    @Override
    public VideoEditionDto editCourseVideo(String courseId, VideoEditionDto videoEditionDto) {

        Course foundCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Course with ID = %s doesn't exist in database!", courseId)));

        if (foundCourse.getSections().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course doesn't existed the section(s)");
        }

        List<Section> newSections = foundCourse.getSections().stream()
                .peek(section -> {
                    if (section.getOrderNo().equals(videoEditionDto.sectionOrderNo())) {
                        List<Video> videos = courseMapper.fromVideoDtoListToVideos(videoEditionDto.videos());
                        section.setVideos(videos);
                    }
                })
                .toList();

        foundCourse.setSections(newSections);
        courseRepository.save(foundCourse);

        return videoEditionDto;
    }


    @Override
    public List<CourseResponse> findCourseByInstructorUsername(String instructorUsername) {
        List<Course> courses = courseRepository.findByInstructorUsername(instructorUsername);
        return courseMapper.toCourseDtoList(courses);
    }

}



