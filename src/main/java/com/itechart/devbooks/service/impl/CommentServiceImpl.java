package com.itechart.devbooks.service.impl;

import com.itechart.devbooks.converter.CommentsConverter;
import com.itechart.devbooks.dao.CommentDao;
import com.itechart.devbooks.entity.CommentEntity;
import com.itechart.devbooks.model.Comment;
import com.itechart.devbooks.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.itechart.devbooks.util.ExceptionUtil.*;

@Slf4j
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao dao;
    private final CommentsConverter converter;

    @Override
    @Transactional(readOnly = true)
    public Comment findById(long id) {
        log.info("Searching for a comment by id: " + id);
        final CommentEntity commentEntity = Optional.ofNullable(dao.findById(id)).orElseThrow(() ->
               notFoundException(String.format("Comment with id %d not found.", id)));
        log.info("Found comment : " + commentEntity);
        return converter.toModel(commentEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Comment> findByOfferId(long id) {
        log.info("Searching for a comment by offer id: " + id);
        final Set<CommentEntity> foundCommentEntities = dao.findByOfferId(id);
        log.info("Found comments: " + foundCommentEntities);
        return converter.toModels(foundCommentEntities);
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        log.info("Saving comment: " + comment);
        final CommentEntity commentEntity = converter.toEntity(comment);
        final CommentEntity savedCommentEntity = dao.save(commentEntity);
        log.info("Saved comment: " + savedCommentEntity);
        return converter.toModel(savedCommentEntity);
    }

    @Override
    @Transactional
    public Comment update(Comment comment) {
        log.info("Updating comment: " + comment);
        if (comment.getId() == null)
            throw illegalArgumentException("Comment without id can not be updated.");
        if (dao.findById(comment.getId()) == null)
            throw notFoundException(String.format("Comment with id %s not found.", comment.getId()));

        final CommentEntity commentEntity = converter.toEntity(comment);
        final CommentEntity updatedCommentEntity = dao.update(commentEntity);
        log.info("Updated comment: " + updatedCommentEntity);
        return converter.toModel(updatedCommentEntity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        log.info("Deleting comment by id: " + id);
        dao.delete(id);
    }
}
