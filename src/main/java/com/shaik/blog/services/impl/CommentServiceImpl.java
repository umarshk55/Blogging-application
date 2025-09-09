package com.shaik.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaik.blog.entities.Comment;
import com.shaik.blog.entities.Post;
import com.shaik.blog.entities.User;
import com.shaik.blog.exceptions.ResourceNotFoundException;
import com.shaik.blog.payloads.CommentDto;
import com.shaik.blog.repositories.CommentRepo;
import com.shaik.blog.repositories.PostRepo;
import com.shaik.blog.repositories.UserRepo;
import com.shaik.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);

        // ⚠️ user is null here → only works if user_id is nullable
        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user); // ✅ attach user

        Comment savedComment = this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment com = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
        this.commentRepo.delete(com);
    }

    @Override
    public List<CommentDto> getCommentsByPost(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        return post.getComments()
                .stream()
                .map(comment -> this.modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }
}
