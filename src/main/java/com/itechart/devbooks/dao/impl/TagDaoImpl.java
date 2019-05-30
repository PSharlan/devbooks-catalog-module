package com.itechart.devbooks.dao.impl;

import com.itechart.devbooks.dao.TagDao;
import com.itechart.devbooks.entity.TagEntity;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TagDaoImpl extends AbstractDao<TagEntity> implements TagDao {

    public TagDaoImpl() { super(TagEntity.class); }

    @Override
    public TagEntity findById(long id) {
        return findOneById(id);
    }

    @Override
    public TagEntity save(TagEntity tag) {
        return create(tag);
    }

    @Override
    public Set<TagEntity> saveAll(Set<TagEntity> tags) {
        return tags.stream().map(this::create).collect(Collectors.toSet());
    }

    @Override
    public TagEntity update(TagEntity tag) {
        return merge(tag);
    }

    @Override
    public void delete(TagEntity tag) {
        remove(tag);
    }

    @Override
    public void delete(long id) {
        remove(findById(id));
    }

    @Override
    public Set<TagEntity> findAll() {
        return new HashSet<>(getAll());
    }

}
