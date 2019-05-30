package com.itechart.devbooks.converter;

import com.itechart.devbooks.entity.CommentEntity;
import com.itechart.devbooks.model.Comment;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommentsConverter {

    final ModelMapper modelMapper;

    public CommentEntity toEntity(Comment Comment) {
        return modelMapper.map(Comment, CommentEntity.class);
    }

    public Comment toModel(CommentEntity Comment) {
        return modelMapper.map(Comment, Comment.class);
    }

    public Set<Comment> toModels(Set<CommentEntity> comments) {
        return comments.stream().map(this::toModel).collect(Collectors.toSet());
    }

    public Set<CommentEntity> toEntities(Set<Comment> comments) {
        return comments.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}