package com.itechart.devbooks.converter;

import com.itechart.devbooks.entity.OfferEntity;
import com.itechart.devbooks.model.Offer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OffersConverter {

    final ModelMapper modelMapper;

    public Offer toModel(OfferEntity offer) {
        return modelMapper.map(offer, Offer.class);
    }

    public OfferEntity toEntity(Offer offer) {
        return modelMapper.map(offer, OfferEntity.class);
    }

    public Set<Offer> toModels(Set<OfferEntity> offers) {
        return offers.stream().map(this::toModel).collect(Collectors.toSet());
    }

    public Set<OfferEntity> toEntities(Set<Offer> offers) {
        return offers.stream().map(this::toEntity).collect(Collectors.toSet());
    }
}
