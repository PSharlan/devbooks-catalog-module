package com.itechart.devbooks.api;

import com.itechart.devbooks.model.Category;
import com.itechart.devbooks.model.Offer;
import com.itechart.devbooks.model.Tag;
import com.itechart.devbooks.service.OfferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/catalog/offers")
@Api(value = "/api/v1/catalog/offers", description = "Manage offers")
public class OffersController {

    private final OfferService offerService;

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return all existing offers")
    @RequestMapping(method = RequestMethod.GET)
    public Set<Offer> getAllOffers() {
        return offerService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return set of an offers by ids")
    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public Set<Offer> getOffersByIds(@RequestParam Set<Integer> ids) {
        return offerService.findById(ids);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return offer by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Offer getOfferById(
            @ApiParam(value = "Id of an offer to lookup for", required = true)
            @PathVariable long id) {
        return offerService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create offer", notes = "Required offer instance")
    @RequestMapping(method = RequestMethod.POST)
    public Offer createOffer(
            @ApiParam(value = "Offer instance", required = true)
            @Valid @RequestBody Offer offer) {
        return offerService.save(offer);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update offer", notes = "Required offer instance")
    @RequestMapping(method = RequestMethod.PUT)
    public Offer updatedOffer(
            @ApiParam(value = "Offer instance", required = true)
            @Valid @RequestBody Offer offer) {
        return offerService.update(offer);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update category of existing offer", notes = "Instance of existing category is required")
    @RequestMapping(value = "/{id}/categories", method = RequestMethod.PUT)
    public void updateOffersCategory(
            @ApiParam(value = "Id of an offer to update", required = true)
            @PathVariable long id,
            @ApiParam(value = "Category instance", required = true)
            @Valid @RequestBody Category category) {
        offerService.updateOffersCategory(id, category);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Add new tag to existing offer", notes = "Instance of existing tag is required")
    @RequestMapping(value = "/{id}/tags", method = RequestMethod.PUT)
    public void addOffersTag(
            @ApiParam(value = "Id of an offer to update", required = true)
            @PathVariable long id,
            @ApiParam(value = "Tag instance", required = true)
            @Valid @RequestBody Tag tag) {
        offerService.addOffersTag(id, tag);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete tag from existing offer", notes = "Instance of existing tag is required")
    @RequestMapping(value = "/{id}/tags", method = RequestMethod.DELETE)
    public void deleteOfferTag(
            @ApiParam(value = "Id of an offer to update", required = true)
            @PathVariable long id,
            @ApiParam(value = "Tag instance", required = true)
            @RequestBody Tag tag) {
        offerService.deleteOffersTag(id, tag);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete offer by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOffer(
            @ApiParam(value = "Id of an offer to delete", required = true)
            @PathVariable long id) {
        offerService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return offers filtered by parameters")
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public Set<Offer> getOffersByParams(
            @ApiParam(value = "CategoryEntity id")
            @RequestParam long categoryId,
            @ApiParam(value = "TagEntity id")
            @RequestParam Set<Long> tagIds,
            @ApiParam(value = "Min price")
            @RequestParam double minPrice,
            @ApiParam(value = "Max price")
            @RequestParam double maxPrice) {
        return offerService.findByParams(categoryId, tagIds, minPrice, maxPrice);
    }

}
