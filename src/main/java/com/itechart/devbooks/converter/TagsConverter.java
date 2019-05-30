package com.itechart.devbooks.converter;

import com.itechart.devbooks.entity.TagEntity;
import com.itechart.devbooks.model.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TagsConverter {

    final ModelMapper modelMapper;

    public Tag toModel(TagEntity tag) {
        return modelMapper.map(tag, Tag.class);
    }

    public TagEntity toEntity(Tag tag) {
        return modelMapper.map(tag, TagEntity.class);
    }

    public Set<Tag> toModels(Set<TagEntity> tags) {
        return tags.stream().map(this::toModel).collect(Collectors.toSet());
    }

    public Set<TagEntity> toEntities(Set<Tag> tags) {
        return tags.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
