package com.itechart.devbooks.converter;

import com.itechart.devbooks.entity.CategoryEntity;
import com.itechart.devbooks.model.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoriesConverter {

    final ModelMapper modelMapper;

    public CategoryEntity toEntity(Category category) {
        return modelMapper.map(category, CategoryEntity.class);
    }

    public Category toModel(CategoryEntity category) {
        return modelMapper.map(category, Category.class);
    }

    public Set<Category> toModels(Set<CategoryEntity> categories) {
        return categories.stream().map(this::toModel).collect(Collectors.toSet());
    }

    public Set<CategoryEntity> toEntities(Set<Category> categories) {
        return categories.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
