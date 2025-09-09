package com.shaik.blog.services;

import java.util.List;

import com.shaik.blog.payloads.PostDto;
import com.shaik.blog.payloads.PostResponse;

public interface PostService {

    // create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    // update
    PostDto updatePost(PostDto postDto, Integer postId);

    // delete
    void deletePost(Integer postId);

    // get all posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // get single post
    PostDto getPostById(Integer postId);

    // get all posts by category
    List<PostDto> getPostByCategory(Integer categoryId);

    // get all posts of user
    List<PostDto> getPostByUser(Integer userId);

    // search posts
    List<PostDto> searchPosts(String keyword);

    // like system
    /**
     * Toggle like for a post by a user. 
     * - If user has already liked → it will unlike.
     * - If user hasn’t liked → it will like.
     */
    PostDto toggleLike(Integer postId, Integer userId);
}
