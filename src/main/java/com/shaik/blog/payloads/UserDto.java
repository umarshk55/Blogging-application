package com.shaik.blog.payloads;



import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	@NotBlank(message = "Username is required")
	@Size(min = 4,message = "Username must be min of 4 characters")
	private String name;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email address is not valid !!")
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min = 3, max = 10,message = "Password must be min of 3 chars and max of 10 chars !!")
//	@Pattern(regexp = "")
	private String password;
	
	@NotBlank
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password=password;
	}
}
