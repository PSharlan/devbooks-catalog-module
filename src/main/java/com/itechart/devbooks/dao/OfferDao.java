package com.itechart.devbooks.dao;

import com.itechart.devbooks.entity.CategoryEntity;
import com.itechart.devbooks.entity.OfferEntity;
import com.itechart.devbooks.entity.TagEntity;

import java.util.Set;

public interface OfferDao {

    Set<OfferEntity> findAll();

    OfferEntity findById(long id);

    OfferEntity save(OfferEntity offer);

    OfferEntity update(OfferEntity offer);

    void delete(OfferEntity offer);

    void delete(long id);

    Set<OfferEntity> findByTag(TagEntity tag);

    Set<OfferEntity> findByCategory(CategoryEntity category);

    OfferEntity addTag(OfferEntity offer, TagEntity tag);

    OfferEntity deleteTag(OfferEntity offer, TagEntity tag);

    OfferEntity updateTags(OfferEntity offer, Set<TagEntity> tags);

    OfferEntity updateCategory(OfferEntity offer, CategoryEntity category);
}
