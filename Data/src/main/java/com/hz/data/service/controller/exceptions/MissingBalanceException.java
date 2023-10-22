package com.hz.data.service.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class MissingBalanceException extends  Error {
    public MissingBalanceException() {
        super();
    }
    public MissingBalanceException(String message) {
        super(message);
    }
}
