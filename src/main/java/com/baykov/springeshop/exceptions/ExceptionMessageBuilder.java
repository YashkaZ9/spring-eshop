package com.baykov.springeshop.exceptions;

import org.springframework.validation.BindingResult;

public class ExceptionMessageBuilder {
    public static String buildErrorMessage(BindingResult errors) {
        StringBuilder errorMsg = new StringBuilder();
        errors.getFieldErrors().forEach(error ->
                errorMsg.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("; "));
        return errorMsg.toString();
    }
}
