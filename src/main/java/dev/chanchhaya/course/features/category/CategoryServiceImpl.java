package dev.chanchhaya.course.features.category;

import dev.chanchhaya.course.domain.Category;
import dev.chanchhaya.course.features.category.dto.CategoryEditionRequest;
import dev.chanchhaya.course.features.category.dto.CategoryCreationRequest;
import dev.chanchhaya.course.features.category.dto.CategoryResponse;
import dev.chanchhaya.course.features.category.dto.PopularCategoryResponse;
import dev.chanchhaya.course.features.course.CourseRepository;
import dev.chanchhaya.course.exception.DuplicateCategoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final CategoryMapper categoryMapper;
    private final MongoTemplate mongoTemplate;

    @Value("${stream.image}")
    private String streamImage;

    @Override
    public List<PopularCategoryResponse> findPopularCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> PopularCategoryResponse.builder()
                        .icon(streamImage + category.getIcon())
                        .name(category.getName())
                        .totalCourses(courseRepository.countByCategoryName(category.getName()))
                        .build())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findById(String id) {

        Category foundCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Category with ID = %s doesn't exist in database!", id)));

        return categoryMapper.toCategoryDto(foundCategory);
    }

    @Override
    public CategoryResponse findByUuid(String uuid) {

        Category foundCategory = categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Category with UUID = %s doesn't exist in database!", uuid)));

        return categoryMapper.toCategoryDto(foundCategory);
    }

    @Override
    public List<CategoryResponse> findList() {
        List<Category> categories = categoryRepository.findAllByIsDeletedFalse();
        return categoryMapper.toCategoryDtoList(categories);
    }



    @Override
    public void editById(String id, CategoryEditionRequest editionDto) {

        Category foundCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Category with ID = %s doesn't exist in database!", id)));

        foundCategory.setName(editionDto.name());
        categoryRepository.save(foundCategory);
    }

    @Override
    public void createNew(CategoryCreationRequest creationDto) {
        Category newCategory = categoryMapper.fromCreationDto(creationDto);
        newCategory.setUuid(UUID.randomUUID().toString());
        newCategory.setIsDeleted(false);

        Query query = new Query(Criteria.where("name").is(creationDto.name()));

        if (mongoTemplate.exists(query, Category.class)) {
            throw new DuplicateCategoryException("Duplicate category");
        }

        categoryRepository.save(newCategory);
    }

    @Override
    public void removeById(String id) {

        if (categoryRepository.existsById(id))
            categoryRepository.deleteById(id);

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Category with ID = %s doesn't exist in database!", id));
    }

    @Override
    public void disable(String id) {

        Category foundCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Category with ID = %s doesn't exist in database!", id)));

        foundCategory.setIsDeleted(true);
        categoryRepository.save(foundCategory);
    }

    @Override
    public void enable(String id) {

        Category foundCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Category with ID = %s doesn't exist in database!", id)));

        foundCategory.setIsDeleted(false);
        categoryRepository.save(foundCategory);
    }

    @Override
    public Optional<CategoryResponse> findCategoryByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        Category foundCategory = mongoTemplate.findOne(query, Category.class);

        return Optional.ofNullable(foundCategory)
                .map(categoryMapper::toCategoryDto);
    }


}
