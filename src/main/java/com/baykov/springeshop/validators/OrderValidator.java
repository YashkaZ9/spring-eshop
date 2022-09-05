package com.baykov.springeshop.validators;

import com.baykov.springeshop.dtos.OrderInfoDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return OrderInfoDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderInfoDto orderInfoDto = (OrderInfoDto) target;
        if (orderInfoDto.getAddress() == null) {
            errors.rejectValue("address", "", "Order should contain an address.");
        }
    }
}
