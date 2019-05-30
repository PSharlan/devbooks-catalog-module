package com.itechart.devbooks.dao.impl;

import com.itechart.devbooks.dao.CategoryDao;
import com.itechart.devbooks.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class CategoryDaoImpl extends AbstractDao<CategoryEntity> implements CategoryDao {

    public CategoryDaoImpl(){
        super(CategoryEntity.class);
    }

    @Override
    public CategoryEntity findById(long id) {
        return findOneById(id);
    }

    @Override
    public CategoryEntity save(CategoryEntity category) {
        return create(category);
    }

    @Override
    public Set<CategoryEntity> saveAll(Set<CategoryEntity> categories) {
        return categories.stream().map(this::create).collect(Collectors.toSet());
    }

    @Override
    public CategoryEntity update(CategoryEntity category) {
        return merge(category);
    }

    @Override
    public void delete(CategoryEntity category) {
        remove(category);
    }

    @Override
    public Set<CategoryEntity> findAll() {
        return new HashSet<>(getAll());
    }

    @Override
    public void delete(long id) {
        remove(findById(id));
    }

}
