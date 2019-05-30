package com.itechart.devbooks.dao;

import com.itechart.devbooks.entity.CategoryEntity;

import java.util.Set;

public interface CategoryDao {

    CategoryEntity findById(long id);

    CategoryEntity save(CategoryEntity category);

    Set<CategoryEntity> saveAll(Set<CategoryEntity> categories);

    CategoryEntity update(CategoryEntity category);

    void delete(CategoryEntity category);

    Set<CategoryEntity> findAll();

    void delete(long id);
}
