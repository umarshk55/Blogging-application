package com.shaik.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shaik.blog.entities.Category;
import com.shaik.blog.entities.Post;
import com.shaik.blog.entities.User;
import com.shaik.blog.exceptions.ResourceNotFoundException;
import com.shaik.blog.payloads.PostDto;
import com.shaik.blog.payloads.PostResponse;
import com.shaik.blog.repositories.CategoryRepo;
import com.shaik.blog.repositories.PostRepo;
import com.shaik.blog.repositories.UserRepo;
import com.shaik.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        // ✅ If no image name is provided, use default.png
        if (postDto.getImageName() == null || postDto.getImageName().trim().isEmpty()) {
            post.setImageName("default.png");
        } else {
            post.setImageName(postDto.getImageName());
        }

        Post newPost = this.postRepo.save(post);
        PostDto response = this.modelMapper.map(newPost, PostDto.class);
        response.setLikeCount(newPost.getLikedBy().size());
        return response;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setCategory(category);

        Post updatedPost = this.postRepo.save(post);
        PostDto response = this.modelMapper.map(updatedPost, PostDto.class);
        response.setLikeCount(updatedPost.getLikedBy().size());
        return response;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        // delete comments tied to this post
        post.getComments().clear();

        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent();

        List<PostDto> postDtos = allPosts.stream().map(post -> {
            PostDto dto = this.modelMapper.map(post, PostDto.class);
            dto.setLikeCount(post.getLikedBy().size());
            return dto;
        }).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        PostDto dto = this.modelMapper.map(post, PostDto.class);
        dto.setLikeCount(post.getLikedBy().size());
        return dto;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {

        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        List<Post> posts = this.postRepo.findByCategory(cat);

        List<PostDto> postDtos = posts.stream().map(post -> {
            PostDto dto = this.modelMapper.map(post, PostDto.class);
            dto.setLikeCount(post.getLikedBy().size());
            return dto;
        }).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        List<Post> posts = this.postRepo.findByUser(user);

        List<PostDto> postDtos = posts.stream().map(post -> {
            PostDto dto = this.modelMapper.map(post, PostDto.class);
            dto.setLikeCount(post.getLikedBy().size());
            return dto;
        }).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        System.out.println("Searching for keyword: " + keyword);

        List<Post> posts = this.postRepo.searchByTitle("%" + keyword + "%");
        List<PostDto> postDtos = posts.stream().map(post -> {
            PostDto dto = this.modelMapper.map(post, PostDto.class);
            dto.setLikeCount(post.getLikedBy().size());
            return dto;
        }).collect(Collectors.toList());
        return postDtos;
    }

    // ✅ Like / Unlike system
    @Override
    public PostDto toggleLike(Integer postId, Integer userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        if (post.getLikedBy().contains(user)) {
            post.getLikedBy().remove(user); // unlike
        } else {
            post.getLikedBy().add(user); // like
        }

        Post updatedPost = postRepo.save(post);
        PostDto postDto = modelMapper.map(updatedPost, PostDto.class);
        postDto.setLikeCount(updatedPost.getLikedBy().size());
        return postDto;
    }
}
