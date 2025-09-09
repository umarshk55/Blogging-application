package com.shaik.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shaik.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
	

}
