package com.shaik.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shaik.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
