package com.itechart.devbooks.dao.impl;

import com.itechart.devbooks.dao.CommentDao;
import com.itechart.devbooks.entity.CommentEntity;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class CommentDaoImpl extends AbstractDao<CommentEntity> implements CommentDao {

    private static final String findByOfferIdQuery = "SELECT c FROM CommentEntity c where c.offer = ";

    public CommentDaoImpl() {
        super(CommentEntity.class);
    }

    @Override
    public CommentEntity findById(long id) {
        return findOneById(id);
    }

    @Override
    public Set<CommentEntity> findByOfferId(long id) {
        return new HashSet<CommentEntity>(getEntityManager().createQuery(findByOfferIdQuery + id).getResultList());
    }

    @Override
    public CommentEntity save(CommentEntity comment) {
        return create(comment);
    }

    @Override
    public CommentEntity update(CommentEntity comment) {
        return merge(comment);
    }

    @Override
    public void delete(CommentEntity comment) {
        remove(comment);
    }

    @Override
    public void delete(long id) {
        delete(findById(id));
    }
}
