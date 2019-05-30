package com.itechart.devbooks.api;

import com.itechart.devbooks.model.Comment;
import com.itechart.devbooks.service.CommentService;
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
@RequestMapping("/api/v1/catalog/offers")
@Api(value = "/api/v1/catalog/offers", description = "Manage offers comments")
public class CommentsController {
    
    private final CommentService commentService;

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return set of a comments by offer id")
    @RequestMapping(value = "/{offerId}/comments", method = RequestMethod.GET)
    public Set<Comment> getCommentsByOfferId(@PathVariable long offerId) {
        return commentService.findByOfferId(offerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Return single comment by id")
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
    public Comment getCommentById(@PathVariable long id) {
        return commentService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create CommentEntity", notes = "Required comment instance")
    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public Comment createComment(
            @ApiParam(value = "CommentEntity instance", required = true)
            @Valid @RequestBody Comment comment) {
        return commentService.save(comment);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update comment", notes = "Required comment instance")
    @RequestMapping(value = "/comments", method = RequestMethod.PUT)
    public Comment updateComment(
            @ApiParam(value = "CommentEntity instance", required = true)
            @Valid @RequestBody Comment comment) {
        return commentService.update(comment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete comment", notes = "Required comment id")
    @RequestMapping(value = "/comments/{id}", method = RequestMethod.DELETE)
    public void deleteComment(
            @ApiParam(value = "CommentEntity id", required = true)
            @PathVariable long id) {
        commentService.delete(id);
    }
}
