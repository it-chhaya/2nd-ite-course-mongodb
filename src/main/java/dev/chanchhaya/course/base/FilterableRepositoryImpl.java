package dev.chanchhaya.course.base;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FilterableRepositoryImpl<T> implements FilterableRepository<T> {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<T> findAllWithFilter(Class<T> typeParameterClass, Filtering filtering, Pageable pageable) {
        Query query = constructQueryFromFiltering(filtering).with(pageable);
        List<T> ts = mongoTemplate.find(query, typeParameterClass);
        return PageableExecutionUtils.getPage(ts, pageable, () -> mongoTemplate.count(query, typeParameterClass));
    }

    @Override
    public List<Object> getAllPossibleValuesForFilter(Class<T> typeParameterClass, Filtering filtering, String filterKey) {
        Query query = constructQueryFromFiltering(filtering);
        return mongoTemplate.query(typeParameterClass).distinct(filterKey).matching(query).all();
    }

}
