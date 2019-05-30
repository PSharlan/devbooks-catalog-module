package com.itechart.devbooks.api;

import com.itechart.devbooks.model.Category;
import com.itechart.devbooks.service.CategoryService;
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
@RequestMapping("/api/v1/catalog/categories")
@Api(value = "/api/v1/catalog/categories", description = "Manage categories")
public class CategoriesController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Return all existing categories")
    public Set<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Return category by id")
    public Category getCategoryById(
            @ApiParam(value = "Id of a category to lookup for", required = true)
            @PathVariable long id) {
        return categoryService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create category", notes = "Required category instance")
    public Category createCategory(
            @ApiParam(value = "CategoryEntity instance")
            @Valid @RequestBody Category category) {
        return categoryService.save(category);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/set", method = RequestMethod.POST)
    @ApiOperation(value = "Create set of categories", notes = "List of categories is required")
    public void createCategories(
            @ApiParam(value = "Set of categories", required = true)
            @Valid @RequestBody Set<Category> categories) {
        categoryService.saveAll(categories);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "Update category", notes = "Required category instance")
    public Category updateCategory(
            @ApiParam(value = "CategoryEntity instance")
            @Valid @RequestBody Category category) {
        return categoryService.update(category);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete category by id")
    public void deleteCategory(
            @ApiParam(value = "Id of a category to delete", required = true)
            @PathVariable long id) {
        categoryService.delete(id);
    }
}
