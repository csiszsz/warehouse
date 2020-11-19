package com.ikea.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ArticleNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Couldn't find article with id: %d.";

    public ArticleNotFoundException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
