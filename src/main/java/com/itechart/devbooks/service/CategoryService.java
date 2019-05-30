package com.itechart.devbooks.service;

import com.itechart.devbooks.entity.CategoryEntity;
import com.itechart.devbooks.model.Category;

import java.util.Set;

/**
 * Provides the service for CRUD operations with categories.
 *
 * @see CategoryEntity
 *
 * @author Pavel Sharlan
 * @version  1.0
 */
public interface CategoryService {

    /**
     * Returns a single category by id.
     *
     * @param id - category id
     * @return - category
     */
    Category findById(long id);

    /**
     * Returns all categories.
     *
     * @return - list of all categories.
     */
    Set<Category> findAll();

    /**
     * Saves single category.
     *
     * @param category - category to save
     * @return saved category
     */
    Category save(Category category);

    /**
     * Saves set of categories.
     *
     * @param categories - categories to save
     */
    Set<Category> saveAll(Set<Category> categories);

    /**
     * Updates single category.
     * Note: Method will return null if category would be not found in Database
     *
     * @param category - category to update
     * @return updated category
     */
    Category update(Category category);

    /**
     * Deletes single category.
     * Note: category should not be associated with any offers.
     *
     * @param id -  id of the category to delete
     */
    void delete(long id);
}
