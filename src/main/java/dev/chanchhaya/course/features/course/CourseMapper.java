package dev.chanchhaya.course.features.course;

import dev.chanchhaya.course.domain.Course;
import dev.chanchhaya.course.domain.Video;
import dev.chanchhaya.course.features.course.dto.*;
import dev.chanchhaya.course.utils.StreamUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CourseMapper {

    StreamUtil streamUtil;

    @Autowired
    public void setStreamUtil(StreamUtil streamUtil) {
        this.streamUtil = streamUtil;
    }

    String getImageUri() {
        return streamUtil.buildImageUri();
    }

    @Mapping(target = "thumbnail", expression = "java(getImageUri() + course.getThumbnail())")
    abstract CourseSnippetResponse toCourseSnippetDto(Course course);

    @Mapping(target = "thumbnail", expression = "java(getImageUri() + course.getThumbnail())")
    abstract CourseResponse toCourseDto(Course course);

    abstract List<CourseResponse> toCourseDtoList(List<Course> courses);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    abstract void mapFromEditionDto(CourseEditionDto editionDto, @MappingTarget Course course);

    abstract Course fromCreationDto(CourseCreationRequest creationDto);

    abstract List<Video> fromVideoDtoListToVideos(List<VideoDto> videoDtoList);

    abstract VideoDto toVideoDto(Video video);

}