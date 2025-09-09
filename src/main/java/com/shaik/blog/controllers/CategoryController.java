package com.shaik.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shaik.blog.payloads.ApiResponse;
import com.shaik.blog.payloads.CategoryDto;
import com.shaik.blog.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Operation(summary = "Create category", description = "Create a new category")
	 // ✅ Create category
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCategory);
    }

	@Operation(summary = "Update category", description = "Update an existing category by ID")
    // ✅ Update category
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            @PathVariable Integer catId) {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, catId);
        return ResponseEntity.ok(updatedCategory);
    }

	@Operation(summary = "Delete category", description = "Delete a category by ID")
    // ✅ Delete category
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
        this.categoryService.deleteCategory(catId);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully!", true));
    }

	@Operation(summary = "Get single category", description = "Retrieve a category by its ID")
    // ✅ Get single category
    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
        CategoryDto categoryDto = this.categoryService.getCategory(catId);
        return ResponseEntity.ok(categoryDto);
    }

	@Operation(summary = "Get all categories", description = "Retrieve all categories")
    // ✅ Get all categories
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> categories = this.categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

}
