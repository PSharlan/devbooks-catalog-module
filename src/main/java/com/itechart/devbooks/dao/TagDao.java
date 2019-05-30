package com.itechart.devbooks.dao;

import com.itechart.devbooks.entity.TagEntity;

import java.util.Set;

public interface TagDao {

    TagEntity findById(long id);

    TagEntity save(TagEntity tag);

    Set<TagEntity> saveAll(Set<TagEntity> tags);

    TagEntity update(TagEntity tag);

    void delete(TagEntity tag);

    void delete(long id);

    Set<TagEntity> findAll();
}
