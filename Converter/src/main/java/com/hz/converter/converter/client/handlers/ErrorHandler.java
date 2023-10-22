package com.hz.converter.converter.client.handlers;

import com.hz.converter.converter.client.exception.AccountServiceUnavailableException;
import com.hz.converter.converter.client.exception.NbpServiceUnavailableException;
import com.hz.converter.converter.client.exception.UnsufficientFoundsExeption;
import com.hz.converter.converter.client.response.GlobalErrorResponse;
import com.hz.converter.converter.controller.exception.EmptyRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({NbpServiceUnavailableException.class, AccountServiceUnavailableException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public GlobalErrorResponse handleServiceUnavailableException(Error e){
        return new GlobalErrorResponse().message(e.getMessage());
    }

    @ExceptionHandler({UnsufficientFoundsExeption.class, EmptyRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalErrorResponse handleAppIdException(Error e){
        return new GlobalErrorResponse().message(e.getMessage());
    }
}
