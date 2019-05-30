package com.itechart.devbooks.service.impl;

import com.itechart.devbooks.converter.CategoriesConverter;
import com.itechart.devbooks.dao.CategoryDao;
import com.itechart.devbooks.entity.CategoryEntity;
import com.itechart.devbooks.model.Category;
import com.itechart.devbooks.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.itechart.devbooks.util.ExceptionUtil.illegalArgumentException;
import static com.itechart.devbooks.util.ExceptionUtil.notFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao dao;
    private final CategoriesConverter converter;

    @Override
    @Transactional(readOnly = true)
    public Category findById(long id) {
        log.info("Searching for a category by id: " + id);
        CategoryEntity category = Optional.ofNullable(dao.findById(id)).orElseThrow(() ->
                notFoundException(String.format("Category with id %d not found.", id)));
        log.info("Found category : " + category);
        return converter.toModel(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Category> findAll() {
        log.info("Searching for all categories");
        final Set<CategoryEntity> set = dao.findAll();
        log.info("Found categories: " + set);
        return converter.toModels(set);
    }

    @Override
    @Transactional
    public Category save(Category category) {
        log.info("Saving category : " + category);
        final CategoryEntity categoryEntity = converter.toEntity(category);
        final CategoryEntity savedCategoryEntity = dao.save(categoryEntity);
        log.info("Saved category : " + savedCategoryEntity);
        return converter.toModel(savedCategoryEntity);
    }

    @Override
    @Transactional
    public Set<Category> saveAll(Set<Category> categories) {
        log.info("Saving categories : " + categories);
        final Set<CategoryEntity> categoryEntities = converter.toEntities(categories);
        final Set<CategoryEntity> savedCategoryEntities = dao.saveAll(categoryEntities);
        log.info("Saved categories : " + categoryEntities);
        return converter.toModels(savedCategoryEntities);
    }

    @Override
    @Transactional
    public Category update(Category category) {
        log.info("Updating category : " + category);
        if (category.getId() == null)
            throw illegalArgumentException("Category without id can not be updated.");
        if (dao.findById(category.getId()) == null)
            throw notFoundException(String.format("Category with id %s not found.", category.getId()));

        final CategoryEntity categoryEntity = converter.toEntity(category);
        final CategoryEntity updatedCategoryEntity = dao.update(categoryEntity);
        log.info("Updated category : " + updatedCategoryEntity);
        return converter.toModel(updatedCategoryEntity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        log.info("Deleting category with id: " + id);
        dao.delete(id);
    }
}