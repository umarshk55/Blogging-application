package com.shaik.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	String resorceName;
	String fieldName;
	long fieldValue;
	public ResourceNotFoundException(String resorceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resorceName,fieldName,fieldValue));
		this.resorceName = resorceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

}
