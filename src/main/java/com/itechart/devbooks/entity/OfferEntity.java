package com.itechart.devbooks.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an OfferEntity, providing access to the offer's id, name,
 * description, price, category and tags.
 *
 * @author Pavel Sharlan
 * @version 1.0
 * @see CategoryEntity
 * @see TagEntity
 */
@Data
@Entity
@Table(name = "offer")
public class OfferEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private CategoryEntity category;

    @Column(name = "price", nullable = false)
    private double price;

    /**
     * The default tags value is a new parameterized HashSet.
     * Note: FetchType.LAZY
     */
    @JoinTable(
            name = "tag_offer",
            joinColumns = {@JoinColumn(name = "offer_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<TagEntity> tags = new HashSet<>();
}
