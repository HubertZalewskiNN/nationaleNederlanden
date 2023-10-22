package com.hz.data.handlers;

import com.hz.data.service.controller.exceptions.AccountNotFoundExeption;
import com.hz.data.service.controller.exceptions.AppIdNullException;
import com.hz.data.service.controller.exceptions.MissingBalanceException;
import com.hz.data.service.model.GlobalErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({AppIdNullException.class, AccountNotFoundExeption.class, MissingBalanceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GlobalErrorResponse handleAppIdException(Error e) {
        return new GlobalErrorResponse().message(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
