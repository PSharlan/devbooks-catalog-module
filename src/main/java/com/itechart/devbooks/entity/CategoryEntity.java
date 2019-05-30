package com.itechart.devbooks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Represents a CategoryEntity, providing access to the category id, name and
 * associated offers.
 *
 * @author Pavel Sharlan
 * @version 1.0
 * @see OfferEntity
 */
@Data
@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * The default offers value is a new parameterized HashSet.
     * Note: FetchType.LAZY
     */
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private Set<OfferEntity> offers = new HashSet<>();

}
