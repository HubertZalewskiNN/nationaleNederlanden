package com.hz.rest.api.api.client.handler;

import com.hz.rest.api.api.client.exception.AccountCreationException;
import com.hz.rest.api.api.client.exception.AccountServiceUnavailableException;
import com.hz.rest.api.api.client.exception.ConverterServiceUnavailableException;
import com.hz.rest.api.api.controller.exception.GlobalErrorResponse;
import com.hz.rest.api.api.controller.exception.NoAccountException;
import com.hz.rest.api.api.controller.exception.NoAppIdException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({AccountServiceUnavailableException.class, ConverterServiceUnavailableException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public GlobalErrorResponse handleServiceUnavailableException(Error e){
        return new GlobalErrorResponse().message(e.getMessage());
    }

    @ExceptionHandler({AccountCreationException.class, NoAppIdException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalErrorResponse handleAppIdException(Error e){
        return new GlobalErrorResponse().message(e.getMessage());
    }
    @ExceptionHandler(NoAccountException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GlobalErrorResponse handleNotFoundException(Error e){
        return new GlobalErrorResponse().message(e.getMessage());
    }
}
