package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Post;

@Component
public class AddPostFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return Post.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.post.title.empty");
	}
} 