package com.hz.converter.converter.client.handlers;

import com.hz.converter.converter.client.exception.AccountServiceUnavailableException;
import com.hz.converter.converter.client.exception.NbpServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class AccountErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()) {
            log.error("Account server error: " + response.getStatusCode());
            throw new AccountServiceUnavailableException("Account server error: " + response.getStatusCode());
        } else if (response.getStatusCode().is4xxClientError()) {
            log.error("Account client error " + response.getStatusCode());
            throw new AccountServiceUnavailableException("Account client error " + response.getStatusCode() + ". Try again later");
        }
    }
}
