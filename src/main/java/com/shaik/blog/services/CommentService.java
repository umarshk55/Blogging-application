package com.shaik.blog.services;

import java.util.List;

import com.shaik.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
	CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
	List<CommentDto> getCommentsByPost(Integer postId);


}
