package com.ikea.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Couldn't find product with id: %d.";

    public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
