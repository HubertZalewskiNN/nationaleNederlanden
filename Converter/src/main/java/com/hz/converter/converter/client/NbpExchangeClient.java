package com.hz.converter.converter.client;

import com.hz.converter.converter.client.exception.NbpServiceUnavailableException;
import com.hz.converter.converter.client.handlers.NbpErrorHandler;
import com.hz.converter.converter.client.response.nbp.NbpExchangeRate;
import com.hz.converter.converter.client.response.nbp.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NbpExchangeClient {

    @Autowired
    private RestTemplate restTemplate;

    public Rate getExchangeRateUSD() throws NbpServiceUnavailableException {
        restTemplate.setErrorHandler(new NbpErrorHandler());
        NbpExchangeRate nbpResponse = restTemplate.getForObject("http://api.nbp.pl/api/exchangerates/rates/c/usd/today/", NbpExchangeRate.class);
        if (nbpResponse != null && nbpResponse.getRates() != null && !nbpResponse.getRates().isEmpty()) {
            return nbpResponse.getRates().get(0);
        }
        throw new NbpServiceUnavailableException("NBP did not return rates");
    }

}
