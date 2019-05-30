package com.itechart.devbooks.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class Comment {

    private Long id;

    @NotNull(message = "CommentEntity w/o OfferEntity")
    private Offer offer;

    @NotEmpty(message = "CommentEntity is empty")
    private String message;

    @Min(value = 1, message = "CommentEntity rating less then 1")
    @Max(value = 5, message = "CommentEntity rating more then 5")
    private int rating;

    @NotNull(message = "CommentEntity w/o Author")
    private long customerId;

    private long editorCustomerId;

    @NotNull(message = "CommentEntity w/o Creation time")
    private Timestamp creationTime;

    private Timestamp editionTime;

}
