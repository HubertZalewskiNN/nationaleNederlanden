package com.hz.converter.converter.controller.exception;

public class EmptyRequestException extends Error {
    public EmptyRequestException() {
        super();
    }

    public EmptyRequestException(String message) {
        super(message);
    }
}
