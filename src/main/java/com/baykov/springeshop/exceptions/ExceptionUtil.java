package com.baykov.springeshop.exceptions;

import org.springframework.validation.BindingResult;

import java.lang.reflect.Constructor;

public class ExceptionUtil {
    public static String buildExceptionMessage(BindingResult errors) {
        StringBuilder errorMsg = new StringBuilder();
        errors.getFieldErrors().forEach(error ->
                errorMsg.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("; "));
        return errorMsg.toString();
    }

    public static void checkExceptions(BindingResult errors, Class<? extends Exception> exception) throws Exception {
        if (errors.hasErrors()) {
            String errorMsg = buildExceptionMessage(errors);
            Constructor<? extends Exception> constructor = exception.getConstructor(String.class);
            throw constructor.newInstance(errorMsg);
        }
    }
}
