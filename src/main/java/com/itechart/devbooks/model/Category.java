package com.itechart.devbooks.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Category {

    private Long id;

    @NotEmpty(message = "CategoryEntity name can not be empty")
    private String name;

}
