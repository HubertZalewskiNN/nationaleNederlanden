package com.hz.rest.api.api.client;

import com.hz.rest.api.api.client.handler.ConverterErrorHandler;
import com.hz.rest.api.api.client.request.ExchangeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ConverterClient {

    @Autowired
    private RestTemplate restTemplate;

    public void exchange(ExchangeRequest exchangeRequest) {
        restTemplate.setErrorHandler(new ConverterErrorHandler());
        restTemplate.put("http://localhost:8080/hz4nndao/accountDao/account/create", exchangeRequest);
    }
}
