package dev.chanchhaya.course.features.category;

import dev.chanchhaya.course.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findByUuid(String uuid);
    List<Category> findAllByIsDeletedFalse();
    Optional<Category> findByName(String name);



}
