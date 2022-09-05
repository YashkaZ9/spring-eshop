package com.baykov.springeshop.validators;

import com.baykov.springeshop.dtos.ProductImagesDto;
import com.baykov.springeshop.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ProductValidator implements Validator {
    private final ProductRepo productRepo;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductImagesDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductImagesDto productDto = (ProductImagesDto) target;
        if (productDto.getTitle() == null) {
            errors.rejectValue("title", "", "Product should have a title.");
        } else if (productDto.getPrice() == null) {
            errors.rejectValue("price", "", "Product should have a price.");
        } else if (productRepo.findByTitle(productDto.getTitle()).isPresent()) {
            errors.rejectValue("title", "", "This product already exists.");
        }
    }
}
