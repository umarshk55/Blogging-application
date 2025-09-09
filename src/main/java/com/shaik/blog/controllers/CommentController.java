package com.shaik.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shaik.blog.payloads.ApiResponse;
import com.shaik.blog.payloads.CommentDto;
import com.shaik.blog.services.CommentService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "Create comment", description = "Add a comment to a post without user")
    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto comment,
            @PathVariable Integer postId) {
        CommentDto createdComment = this.commentService.createComment(comment, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @Operation(summary = "Create comment with user", description = "Add a comment to a post by a specific user")
    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDto> createCommentWithUser(
            @RequestBody CommentDto comment,
            @PathVariable Integer postId,
            @PathVariable Integer userId) {
        CommentDto createdComment = this.commentService.createComment(comment, postId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @Operation(summary = "Delete comment", description = "Delete a comment by its ID")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully!", true));
    }

    @Operation(summary = "Get comments for a post", description = "Fetch all comments by postId")
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Integer postId) {
        List<CommentDto> comments = this.commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }
}
