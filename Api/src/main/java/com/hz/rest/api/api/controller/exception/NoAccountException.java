package com.hz.rest.api.api.controller.exception;


public class NoAccountException extends Error {
    public NoAccountException() {
        super();
    }
    public NoAccountException(String message) {
        super(message);
    }
}
