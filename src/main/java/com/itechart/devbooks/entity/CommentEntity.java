package com.itechart.devbooks.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="comment")
public class CommentEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name= "offer", nullable = false)
    private OfferEntity offer;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "customer_id", nullable = false)
    private long customerId;

    @Column(name = "editor_customer_id")
    private long editorCustomerId;

    @Column(name = "creation_time", nullable = false)
    private Timestamp creationTime;

    @Column(name = "edition_time")
    private Timestamp editionTime;
}
