package com.baykov.springeshop.exceptions;

public class PersonNotCreatedException extends RuntimeException {
    public PersonNotCreatedException(String msg) {
        super(msg);
    }
}
