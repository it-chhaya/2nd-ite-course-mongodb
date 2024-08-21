package dev.chanchhaya.course.features.category;

import dev.chanchhaya.course.domain.Category;
import dev.chanchhaya.course.features.category.dto.CategoryCreationRequest;
import dev.chanchhaya.course.features.category.dto.CategoryResponse;
import dev.chanchhaya.course.utils.StreamUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CategoryMapper {

    StreamUtil streamUtil;

    @Autowired
    public void setStreamUtil(StreamUtil streamUtil) {
        this.streamUtil = streamUtil;
    }

    String getImageUri() {
        return streamUtil.buildImageUri();
    }

    @Mapping(target = "icon", expression = "java(getImageUri() + category.getIcon())")
    abstract CategoryResponse toCategoryDto(Category category);

    abstract List<CategoryResponse> toCategoryDtoList(List<Category> categories);

    abstract Category fromCreationDto(CategoryCreationRequest creationDto);

}
