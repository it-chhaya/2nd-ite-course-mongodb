package dev.chanchhaya.course.features.category;

import dev.chanchhaya.course.features.category.dto.CategoryCreationRequest;
import dev.chanchhaya.course.features.category.dto.CategoryEditionRequest;
import dev.chanchhaya.course.features.category.dto.CategoryResponse;
import dev.chanchhaya.course.features.category.dto.PopularCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;


    @GetMapping("/popular")
    List<PopularCategoryResponse> findPopularCategories() {
        return categoryService.findPopularCategories();
    }


    @GetMapping("/{id}")
    CategoryResponse findById(@PathVariable String id) {
        return categoryService.findById(id);
    }


    /*@GetMapping("/uuid/{uuid}")
    CategoryResponse findByUuid(@PathVariable String uuid) {
        return categoryService.findByUuid(uuid);
    }*/


    @GetMapping
    List<CategoryResponse> findList() {
        return categoryService.findList();
    }


    @PutMapping("/{id}")
    CategoryResponse editById(@PathVariable String id, @Valid @RequestBody CategoryEditionRequest categoryEditionRequest) {
        categoryService.editById(id, categoryEditionRequest);
        return categoryService.findById(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody CategoryCreationRequest categoryCreationRequest) {
        categoryService.createNew(categoryCreationRequest);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void removeById(@PathVariable String id) {
        categoryService.removeById(id);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/disable")
    void disable(@PathVariable String id) {
        categoryService.disable(id);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/enable")
    void enable(@PathVariable String id) {
        categoryService.enable(id);
    }


    /*@GetMapping("/{name}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String name) {
        Optional<CategoryResponse> categoryDto = categoryService.findCategoryByName(name);
        return categoryDto
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/

}
