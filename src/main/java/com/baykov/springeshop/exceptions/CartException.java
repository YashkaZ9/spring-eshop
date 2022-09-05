package com.baykov.springeshop.exceptions;

public class CartException extends RuntimeException{
    public CartException(String message) {
        super(message);
    }
}
