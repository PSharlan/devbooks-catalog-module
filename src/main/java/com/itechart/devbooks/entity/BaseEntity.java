package com.itechart.devbooks.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@ToString
@EqualsAndHashCode
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
