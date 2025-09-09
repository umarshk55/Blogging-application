package com.shaik.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter	
@Setter
public class CategoryDto {
	private Integer categoryId;
	@NotBlank
	@Size(min = 4,message="Min size of category title is 4")
	private String categoryTitle;
	@NotBlank
	@Size(min = 50,message="min size of category desc is 30")
	private String categoryDescription;

}
