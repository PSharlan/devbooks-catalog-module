package com.itechart.devbooks.service;

import com.itechart.devbooks.model.Tag;

import java.util.Set;

/**
 * Provides the service for CRUD operations with tags.
 *
 * @see Tag
 *
 * @author Pavel Sharlan
 * @version  1.0
 */
public interface TagService {

    /**
     * Returns a single tag by id.
     *
     * @param id - tag id
     * @return - tag
     */
    Tag findById(long id);

    /**
     * Returns all tags.
     *
     * @return - Set of all tags.
     */
    Set<Tag> findAll();

    /**
     * Saves single tag.
     *
     * @param tag - tag to save
     * @return saved tag
     */
    Tag save(Tag tag);

    /**
     * Saves set of tags.
     *
     * @param tags - tags to save
     */
    Set<Tag> saveAll(Set<Tag> tags);

    /**
     * Updates single tag.
     * Note: Method will return null if tag would be not found in Database
     *
     * @param tag - tag to update
     * @return updated tag
     */
    Tag update(Tag tag);

    /**
     * Deletes single tag.
     * Note: tag should not be associated with any offers.
     *
     * @param id - id of the tag to delete
     */
    void delete(long id);
}
