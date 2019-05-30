package com.itechart.devbooks.service.impl;

import com.itechart.devbooks.converter.CategoriesConverter;
import com.itechart.devbooks.converter.OffersConverter;
import com.itechart.devbooks.converter.TagsConverter;
import com.itechart.devbooks.dao.OfferDao;
import com.itechart.devbooks.dao.TagDao;
import com.itechart.devbooks.entity.CategoryEntity;
import com.itechart.devbooks.entity.OfferEntity;
import com.itechart.devbooks.entity.TagEntity;
import com.itechart.devbooks.model.Category;
import com.itechart.devbooks.model.Offer;
import com.itechart.devbooks.model.Tag;
import com.itechart.devbooks.service.OfferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.itechart.devbooks.util.ExceptionUtil.illegalArgumentException;
import static com.itechart.devbooks.util.ExceptionUtil.notFoundException;

@Slf4j
@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferDao offerDao;
    private final TagDao tagDao;
    private final OffersConverter offersConverter;
    private final TagsConverter tagsConverter;
    private final CategoriesConverter categoriesConverter;

    @Override
    @Transactional(readOnly = true)
    public Set<Offer> findAll() {
        log.info("Searching for all offers");
        final Set<OfferEntity> foundOfferEntities = offerDao.findAll();
        log.info("Found offers: " + foundOfferEntities);
        return offersConverter.toModels(foundOfferEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public Offer findById(long id) {
        log.info("Searching for offer by id: " + id);
        final OfferEntity foundOfferEntity = Optional.ofNullable(offerDao.findById(id)).orElseThrow(() ->
                notFoundException(String.format("Offer with id %d not found.", id)));
        Hibernate.initialize(foundOfferEntity.getTags());
        log.info("Found offer: " + foundOfferEntity);
        return offersConverter.toModel(foundOfferEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Offer> findById(Set<Integer> ids) {
        log.info("Searching for offers by ids: " + ids);
        final Set<Offer> foundOfferEntities = ids.stream().map(this::findById).collect(Collectors.toSet());
        log.info("Found offers: " + foundOfferEntities);
        return foundOfferEntities;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Offer> findByTag(Tag tag) {
        log.info("Searching for offers by tag: " + tag);
        final TagEntity tagEntity = tagsConverter.toEntity(tag);
        final Set<OfferEntity> foundOfferEntities = offerDao.findByTag(tagEntity);
        log.info("Found offers: " + foundOfferEntities);
        return offersConverter.toModels(foundOfferEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Offer> findByCategory(Category category) {
        log.info("Searching for offers by category: " + category);
        final CategoryEntity categoryEntity = categoriesConverter.toEntity(category);
        final Set<OfferEntity> foundOfferEntities = offerDao.findByCategory(categoryEntity);
        log.info("Found offers: " + foundOfferEntities);
        return offersConverter.toModels(foundOfferEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Offer> findByParams(long categoryId, Set<Long> tagIds, double minPrice, double maxPrice) {
        final Set<TagEntity> tagsForFilter = new HashSet<>();
        if(!tagIds.contains(new Long(0))){
            for (long id : tagIds) {
                tagsForFilter.add(tagDao.findById(id));
            }
        }

        log.info(String.format("Searching for offers by parameters: category id = %s | tag ids = %s | minimum price = %s | maximum price = %s", categoryId, tagIds, minPrice, maxPrice));
        log.info("Found tags: " + tagsForFilter);

        final Set<OfferEntity> allOffers = offerDao.findAll();
        final Set<OfferEntity> filteredOffers = new HashSet<>();
        for (OfferEntity offer: allOffers) {
            if(categoryId == 0 || offer.getCategory().getId() == categoryId){
                if(minPrice == 0 || offer.getPrice() >= minPrice){
                    if(maxPrice == 0 || offer.getPrice() <= maxPrice){
                        if(tagsForFilter.size() == 0) {
                            filteredOffers.add(offer);
                        } else {
                            if(offer.getTags().containsAll(tagsForFilter)){
                                filteredOffers.add(offer);
                            }
                        }
                    }
                }
            }
        }
        log.info("found offers: " + filteredOffers);
        return offersConverter.toModels(filteredOffers);
    }

    @Override
    @Transactional
    public Offer save(Offer offer) {
        log.info("Saving offer : " + offer);
        final OfferEntity offerEntity = offersConverter.toEntity(offer);
        final OfferEntity savedOfferEntity = offerDao.save(offerEntity);
        log.info("Saved offer : " + savedOfferEntity);
        return offersConverter.toModel(savedOfferEntity);
    }

    @Override
    @Transactional
    public Offer update(Offer offer) {
        log.info("Updating offer : " + offer);
        if (offer.getId() == null)
            throw illegalArgumentException("Offer without id can not be updated.");
        if (offerDao.findById(offer.getId()) == null)
            throw notFoundException(String.format("Offer with id %s not found.", offer.getId()));

        final OfferEntity offerEntity = offersConverter.toEntity(offer);
        final OfferEntity updatedOfferEntity = offerDao.update(offerEntity);
        log.info("Updated offer : " + updatedOfferEntity);
        return offersConverter.toModel(updatedOfferEntity);
    }

    @Override
    @Transactional
    public Offer addOffersTag(Offer offer, Tag tag) {
        log.info(String.format("Add tag: %s for Offer: %s", tag, offer));
        final TagEntity tagEntity = tagsConverter.toEntity(tag);
        final OfferEntity offerEntity = offersConverter.toEntity(offer);
        final OfferEntity updatedOfferEntity = offerDao.addTag(offerEntity, tagEntity);
        log.info("Updated offer: " + updatedOfferEntity);
        return offersConverter.toModel(updatedOfferEntity);
    }

    @Override
    @Transactional
    public Offer addOffersTag(Long offerId, Tag tag) {
        return addOffersTag(findById(offerId), tag);
    }

    @Override
    @Transactional
    public Offer deleteOffersTag(Offer offer, Tag tag) {
        log.info(String.format("Deleting tag: %s from Offer: %s", tag, offer));
        final TagEntity tagEntity = tagsConverter.toEntity(tag);
        final OfferEntity offerEntity = offersConverter.toEntity(offer);
        final OfferEntity updatedOfferEntity = offerDao.deleteTag(offerEntity, tagEntity);
        log.info("Updated offer: " + updatedOfferEntity);
        return offersConverter.toModel(updatedOfferEntity);
    }


    @Override
    @Transactional
    public Offer deleteOffersTag(Long offerId, Tag tag) {
        return deleteOffersTag(findById(offerId), tag);
    }

    @Override
    @Transactional
    public Offer updateOffersCategory(Offer offer, Category category) {
        log.info(String.format("Set new category: %s for Offer: %s", category, offer));
        final CategoryEntity categoryEntity = categoriesConverter.toEntity(category);
        final OfferEntity offerEntity = offersConverter.toEntity(offer);
        final OfferEntity updatedOfferEntity = offerDao.updateCategory(offerEntity, categoryEntity);
        log.info("Updated offer: " + updatedOfferEntity);
        return offersConverter.toModel(updatedOfferEntity);
    }

    @Override
    @Transactional
    public Offer updateOffersCategory(Long offerId, Category category) {
        return updateOffersCategory(findById(offerId), category);
    }

    @Override
    @Transactional
    public void delete(long id) {
        log.info("Deleting offer with id: " + id);
        offerDao.delete(id);
    }
}
