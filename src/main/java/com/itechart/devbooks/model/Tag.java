package com.itechart.devbooks.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Tag {

    private Long id;

    @NotEmpty(message = "TagEntity name can not be empty")
    private String name;
}
