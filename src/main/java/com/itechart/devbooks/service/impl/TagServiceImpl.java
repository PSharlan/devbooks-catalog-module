package com.itechart.devbooks.service.impl;

import com.itechart.devbooks.converter.TagsConverter;
import com.itechart.devbooks.dao.TagDao;
import com.itechart.devbooks.entity.TagEntity;
import com.itechart.devbooks.model.Tag;
import com.itechart.devbooks.service.TagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.itechart.devbooks.util.ExceptionUtil.*;

@Slf4j
@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao dao;
    private final TagsConverter converter;

    @Override
    @Transactional(readOnly = true)
    public Tag findById(long id) {
        log.info("Searching for Tag by id: " + id);
        final TagEntity foundTagEntity = Optional.ofNullable(dao.findById(id)).orElseThrow(() ->
               notFoundException(String.format("Tag with id %d not found.", id)));
        Hibernate.initialize(foundTagEntity);
        log.info("Found tag: " + foundTagEntity);
        return converter.toModel(foundTagEntity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Set<Tag> findAll() {
        log.info("Searching for all Tags");
        final Set<TagEntity> foundTagEntities = dao.findAll();
        log.info("Found Tags: " + foundTagEntities);
        return converter.toModels(foundTagEntities);
    }

    @Override
    @Transactional
    public Tag save(Tag tag) {
        log.info("Saving Tag : " + tag);
        final TagEntity tagEntity = converter.toEntity(tag);
        final TagEntity savedTagEntity = dao.save(tagEntity);
        log.info("Saved Tag : " + savedTagEntity);
        return converter.toModel(savedTagEntity);
    }

    @Override
    @Transactional
    public Set<Tag> saveAll(Set<Tag> tags) {
        log.info("Saving Tags : " + tags);
        final Set<TagEntity> tagEntities = converter.toEntities(tags);
        final Set<TagEntity> savedTagEntities = dao.saveAll(tagEntities);
        return converter.toModels(savedTagEntities);
    }

    @Override
    @Transactional
    public Tag update(Tag tag) {
        log.info("Updating Tag : " + tag);
        if (tag.getId() == null)
            throw illegalArgumentException("Tag without id can not be updated.");
        if (dao.findById(tag.getId()) == null)
            throw notFoundException(String.format("Tag with id %s not found.", tag.getId()));

        final TagEntity tagEntity = converter.toEntity(tag);
        final TagEntity updatedTagEntity = dao.update(tagEntity);
        log.info("Updated tag : " + updatedTagEntity);
        return converter.toModel(updatedTagEntity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        log.info("Deleting Tag with id: " + id);
        dao.delete(id);
    }
}
