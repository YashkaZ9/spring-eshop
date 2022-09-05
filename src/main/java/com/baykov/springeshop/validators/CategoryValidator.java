package com.baykov.springeshop.validators;

import com.baykov.springeshop.dtos.CategoryProductsDto;
import com.baykov.springeshop.repos.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CategoryValidator implements Validator {
    private final CategoryRepo categoryRepo;

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryProductsDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CategoryProductsDto categoryDto = (CategoryProductsDto) target;
        if (categoryDto.getTitle() == null) {
            errors.rejectValue("title", "", "Category should have a title.");
        } else if (categoryRepo.findByTitle(categoryDto.getTitle()).isPresent()) {
            errors.rejectValue("title", "", "This category already exists.");
        }
    }
}
