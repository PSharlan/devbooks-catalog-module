package com.itechart.devbooks.dao.impl;

import com.itechart.devbooks.dao.OfferDao;
import com.itechart.devbooks.entity.CategoryEntity;
import com.itechart.devbooks.entity.OfferEntity;
import com.itechart.devbooks.entity.TagEntity;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class OfferDaoImpl extends AbstractDao<OfferEntity> implements OfferDao {

    private static final String findByTagQuery = "SELECT o FROM OfferEntity o join o.tags t where t.id = ";
    private static final String findByCategoryQuery = "SELECT o FROM OfferEntity o where o.category = ";

    public OfferDaoImpl(){
        super(OfferEntity.class);
    }

    @Override
    public Set<OfferEntity> findAll() {
        return new HashSet<>(getAll());
    }

    @Override
    public OfferEntity findById(long id) {
        return findOneById(id);
    }

    @Override
    public OfferEntity save(OfferEntity offer) {
        return create(offer);
    }

    @Override
    public OfferEntity update(OfferEntity offer) {
        return merge(offer);
    }

    @Override
    public void delete(OfferEntity offer) {
        remove(offer);
    }

    @Override
    public void delete(long id) {
       delete(findById(id));
    }


    @Override
    public Set<OfferEntity> findByTag(TagEntity tag) {
       return new HashSet<OfferEntity>(getEntityManager().createQuery(findByTagQuery + tag.getId()).getResultList());
    }

    @Override
    public Set<OfferEntity> findByCategory(CategoryEntity category) {
        return new HashSet<OfferEntity>(getEntityManager().createQuery(findByCategoryQuery + category.getId()).getResultList());
    }

    @Override
    public OfferEntity addTag(OfferEntity offer, TagEntity tag) {
        offer.getTags().add(tag);
        return merge(offer);
    }

    @Override
    public OfferEntity deleteTag(OfferEntity offer, TagEntity tag) {
        offer.getTags().remove(tag);
        return merge(offer);
    }

    @Override
    public OfferEntity updateTags(OfferEntity offer, Set<TagEntity> tags) {
        offer.setTags(tags);
        return merge(offer);
    }

    @Override
    public OfferEntity updateCategory(OfferEntity offer, CategoryEntity category) {
        offer.setCategory(category);
        return merge(offer);
    }

}
