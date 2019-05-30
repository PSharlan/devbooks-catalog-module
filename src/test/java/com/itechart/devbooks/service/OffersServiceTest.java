package com.itechart.devbooks.service;

import com.itechart.devbooks.config.TestConfig;
import com.itechart.devbooks.exception.NotFoundException;
import com.itechart.devbooks.model.Category;
import com.itechart.devbooks.model.Offer;
import com.itechart.devbooks.model.Tag;
import com.itechart.devbooks.service.CategoryService;
import com.itechart.devbooks.service.OfferService;
import com.itechart.devbooks.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
@SqlConfig(dataSource = "pgTestDataSource")
@Sql(value = {"/create.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/drop.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class OffersServiceTest {

    @Autowired
    private OfferService offerService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    private Offer offer;
    private Category category;
    private Tag tag1;
    private Tag tag2;
    private Tag tag3;
    private Set<Tag> tags;

    @BeforeEach
    public void setUp() {
        category = categoryService.findById(1);
        tag1 = tagService.findById(1);
        tag2 = tagService.findById(2);
        tag3 = tagService.findById(3);

        tags = new HashSet<>();
        tags.add(tag1);
        tags.add(tag2);

        offer = offerService.findById(1);
    }

    @Test
    public void saveOffer() {
        Offer offerToSave = new Offer();
        offerToSave.setName("Name");
        offerToSave.setDescription("Description");
        offerToSave.setCategory(category);
        offerToSave.setPrice(100);
        offerToSave.setTags(tags);
        Offer savedOffer = offerService.save(offerToSave);

        assertNotNull(savedOffer);
        assertNotNull(savedOffer.getCategory());
        assertNotNull(savedOffer.getTags());
    }

    @Test
    public void findAllOffers() {
        Set<Offer> foundOffers = offerService.findAll();
        assertNotNull(foundOffers);
        assertTrue(foundOffers.size() > 1);
    }

    @Test
    public void findOfferById() {
        Offer foundOffer = offerService.findById(offer.getId());

        assertNotNull(foundOffer);
        assertNotNull(foundOffer.getTags());
        assertTrue(foundOffer.getTags().size() > 0);
    }

    @Test
    public void exceptionWhileSearchingByIdIfOfferNotFound() {
        assertThrows(NotFoundException.class, () -> offerService.findById(-1));
    }

    @Test
    public void findOffersByIds() {
        Set<Integer> ids = new HashSet<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        Set<Offer> foundOffers = offerService.findById(ids);

        assertNotNull(foundOffers);
        assertEquals(3, foundOffers.size());
    }

    @Test
    public void exceptionWhileSearchingByIdsIfAnyOfferNotFound() {
        Set<Integer> ids = new HashSet<>();
        ids.add(1);
        ids.add(-1);
        ids.add(3);

        assertThrows(NotFoundException.class, () -> offerService.findById(ids));
    }

    @Test
    public void findOffersByTag() {
        Set<Offer> foundOffers = offerService.findByTag(tag2);

        assertNotNull(foundOffers);
        assertTrue(foundOffers.size() > 0);
        assertTrue(foundOffers.size() < offerService.findAll().size());
    }

    @Test
    public void findOffersByCategory() {
        Set<Offer> foundOffers = offerService.findByCategory(category);

        assertNotNull(foundOffers);
        assertTrue(foundOffers.size() > 0);
        assertTrue(foundOffers.size() < offerService.findAll().size());
    }

    @Test
    public void findOffersByParams() {
        Set<Long> tagIds = new HashSet<>();
        tagIds.add(3L);

        Set<Offer> foundOffers = offerService.findByParams(0, tagIds, 200, 0);
        assertNotNull(foundOffers);
        assertTrue(foundOffers.size() < offerService.findAll().size());
        foundOffers.forEach(foundOffer -> {
            assertTrue(foundOffer.getTags().contains(tag3));
            assertTrue(foundOffer.getPrice() > 200);
        });
    }

    @Test
    public void updateOfferName() {
        Offer offerToUpdate = new Offer();
        offerToUpdate.setId(offer.getId());
        offerToUpdate.setCategory(offer.getCategory());
        offerToUpdate.setDescription(offer.getDescription());
        offerToUpdate.setPrice(offer.getPrice());
        offerToUpdate.setTags(offer.getTags());
        offerToUpdate.setName("updatedName");

        Offer updatedOffer = offerService.update(offerToUpdate);

        assertNotNull(updatedOffer);
        assertEquals(offer.getId(), updatedOffer.getId());
        assertEquals(offer.getCategory(), updatedOffer.getCategory());
        assertEquals(offer.getDescription(), updatedOffer.getDescription());
        assertEquals(offer.getTags(), updatedOffer.getTags());

        assertNotEquals(offer.getName(), updatedOffer.getName());
    }

    @Test
    public void exceptionWhileUpdatingIfOfferHasNoId() {
        Offer offerToUpdate = new Offer();
        offerToUpdate.setCategory(offer.getCategory());
        offerToUpdate.setDescription(offer.getDescription());
        offerToUpdate.setPrice(offer.getPrice());
        offerToUpdate.setTags(offer.getTags());
        offerToUpdate.setName("updatedName");

        assertThrows(IllegalArgumentException.class, () -> offerService.update(offerToUpdate));
    }

    @Test
    public void exceptionWhileUpdatingIfOfferNotFound() {
        Offer offerToUpdate = new Offer();
        offerToUpdate.setId(0L);
        offerToUpdate.setCategory(offer.getCategory());
        offerToUpdate.setDescription(offer.getDescription());
        offerToUpdate.setPrice(offer.getPrice());
        offerToUpdate.setTags(offer.getTags());
        offerToUpdate.setName("updatedName");

        assertThrows(NotFoundException.class, () -> offerService.update(offerToUpdate));
    }

    @Test
    public void addSingleTagForExistingOffer() {
        Offer updatedOffer = offerService.addOffersTag(offer.getId(), tag3);
        assertNotNull(updatedOffer);
        assertEquals(updatedOffer.getTags().size(), tags.size() + 1);
        assertTrue(updatedOffer.getTags().contains(tag3));
    }

    @Test
    public void deleteSingleTagFromExistingOffer() {
        Offer updatedOffer = offerService.deleteOffersTag(offer.getId(), tag2);
        assertNotNull(updatedOffer);
        assertEquals(updatedOffer.getTags().size(), offer.getTags().size() - 1);
        assertFalse(updatedOffer.getTags().contains(tag2));
    }

    @Test
    public void changeCategoryOfExistingOffer() {
        Category newCategory = categoryService.findById(2);
        Offer updatedOffer = offerService.updateOffersCategory(offer.getId(), newCategory);

        assertNotNull(updatedOffer);
        assertEquals(updatedOffer.getCategory(), newCategory);
        assertNotNull(categoryService.findById(1));
    }

    @Test
    public void deleteOffer() {
        long id = offer.getId();
        offerService.delete(id);

        Assertions.assertThrows(NotFoundException.class, () -> offerService.findById(id));
    }

}
