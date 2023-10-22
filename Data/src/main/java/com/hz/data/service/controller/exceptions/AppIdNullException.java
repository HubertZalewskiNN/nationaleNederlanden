package com.hz.data.service.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class AppIdNullException extends Error {
    public AppIdNullException() {
        super();
    }
    public AppIdNullException(String message) {
        super(message);
    }
}
