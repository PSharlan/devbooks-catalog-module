package com.itechart.devbooks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a TagEntity, providing access to the category id, name and
 * associated offers.
 *
 * @author Pavel Sharlan
 * @version 1.0
 * @see OfferEntity
 */
@Data
@Entity
@Table(name = "tag")
public class TagEntity extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * The default offers value is a new parameterized HashSet.
     * Note: FetchType.LAZY
     */
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags", cascade = CascadeType.ALL)
    private Set<OfferEntity> offers = new HashSet<OfferEntity>();
}
