package com.ikea.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductImportException extends RuntimeException {
    private static final String MESSAGE = "Error while saving product with name: %s. Makes sure all articles are added to the inventory.";

    public ProductImportException(String name) {
        super(String.format(MESSAGE, name));
    }
}
