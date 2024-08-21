package dev.chanchhaya.course.features.category;

import dev.chanchhaya.course.features.category.dto.CategoryResponse;
import dev.chanchhaya.course.features.category.dto.CategoryCreationRequest;
import dev.chanchhaya.course.features.category.dto.CategoryEditionRequest;
import dev.chanchhaya.course.features.category.dto.PopularCategoryResponse;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<PopularCategoryResponse> findPopularCategories();

    CategoryResponse findById(String id);

    CategoryResponse findByUuid(String uuid);

    List<CategoryResponse> findList();

    void editById(String id, CategoryEditionRequest editionDto);

    void createNew(CategoryCreationRequest creationDto);

    void removeById(String id);

    void disable(String id);

    void enable(String id);

    Optional<CategoryResponse> findCategoryByName(String name);

}
