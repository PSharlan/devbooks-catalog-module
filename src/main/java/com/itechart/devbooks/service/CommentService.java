package com.itechart.devbooks.service;

import com.itechart.devbooks.model.Comment;

import java.util.Set;

public interface CommentService {

    Comment findById(long id);

    Set<Comment> findByOfferId(long id);

    Comment save(Comment comment);

    Comment update(Comment comment);

    void delete(long id);
}
