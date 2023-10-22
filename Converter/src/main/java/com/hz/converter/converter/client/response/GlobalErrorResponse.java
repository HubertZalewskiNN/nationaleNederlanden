package com.hz.converter.converter.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GlobalErrorResponse {
    @JsonProperty("message")
    private String message = null;

    public GlobalErrorResponse message(String message) {
        this.message = message;
        return this;
    }
}
