package com.itechart.devbooks.api;

import com.itechart.devbooks.model.Tag;
import com.itechart.devbooks.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/catalog/tags")
@Api(value = "/api/v1/catalog/tags", description = "Manage tags")
public class TagsController {

    private final TagService tagService;

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return all existing tags")
    @RequestMapping(method = RequestMethod.GET)
    public Set<Tag> getAllTags() {
        return tagService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return tag by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Tag getTagById(
            @ApiParam(value = "Id of tag to lookup for", required = true)
            @PathVariable long id) {
        return tagService.findById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update tag", notes = "Required tag instance")
    @RequestMapping(method = RequestMethod.PUT)
    public Tag updateTag(
            @ApiParam(value = "Tag instance")
            @Valid @RequestBody Tag tag) {
        return tagService.update(tag);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete tag by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTag(
            @ApiParam(value = "Id of a tag to delete", required = true)
            @PathVariable long id) {
        tagService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create tag", notes = "Required tag instance")
    @RequestMapping(method = RequestMethod.POST)
    public Tag createTag(
            @ApiParam(value = "Tag instance")
            @Valid @RequestBody Tag tag) {
        return tagService.save(tag);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create list of tags", notes = "List of tags is required")
    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public void createTags(
            @ApiParam(value = "Set of tags", required = true)
            @Valid @RequestBody Set<Tag> tags) {
        tagService.saveAll(tags);
    }
}
