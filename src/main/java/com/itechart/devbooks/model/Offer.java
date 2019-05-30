package com.itechart.devbooks.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class Offer {

    private Long id;

    @NotEmpty(message = "OfferEntity name can not be empty")
    private String name;

    @NotEmpty(message = "Description can not be empty")
    private String description;

    @NotNull(message = "CategoryEntity have to be chosen")
    private Category category;

    @Min(value = 1, message = "Price can not be less than 1")
    private double price;

    private Set<Tag> tags = new HashSet<>();
}
