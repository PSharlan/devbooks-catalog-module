package com.itechart.devbooks.dao;

import com.itechart.devbooks.entity.CommentEntity;

import java.util.Set;

public interface CommentDao {

    CommentEntity findById(long id);

    Set<CommentEntity> findByOfferId(long id);

    CommentEntity save(CommentEntity comment);

    CommentEntity update(CommentEntity comment);

    void delete(CommentEntity comment);

    void delete(long id);

}
