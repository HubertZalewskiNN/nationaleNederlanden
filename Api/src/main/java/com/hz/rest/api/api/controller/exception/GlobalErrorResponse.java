package com.hz.rest.api.api.controller.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GlobalErrorResponse {
    @JsonProperty("message")
    private String message = null;

    public GlobalErrorResponse message(String message) {
        this.message = message;
        return this;
    }
}
