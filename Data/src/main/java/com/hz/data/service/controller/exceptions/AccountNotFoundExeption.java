package com.hz.data.service.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountNotFoundExeption extends Error{
    public AccountNotFoundExeption() {
        super();
    }
    public AccountNotFoundExeption(String message) {
        super(message);
    }
}
