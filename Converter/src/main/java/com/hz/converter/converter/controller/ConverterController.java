package com.hz.converter.converter.controller;

import com.hz.converter.converter.controller.exception.EmptyRequestException;
import com.hz.converter.converter.exchange.Exchange;
import com.hz.converter.converter.controller.request.ExchangeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConverterController {

    @Autowired
    private Exchange exchange;

    @GetMapping("/converter")
    public void converter(@RequestBody ExchangeRequest request) {
        if (request == null || request.getAppId() == null || request.getAmmount() == null || request.getExchange() == null) {
            throw new EmptyRequestException("All parametres are reqired");
        }
        exchange.exchangeCurrency(request.getAppId(), request.getAmmount(), request.getExchange());
    }
}