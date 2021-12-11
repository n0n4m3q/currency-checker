package com.currency.checker.currencychecker.service;

import com.currency.checker.currencychecker.config.CurrencyServicesConfig;
import com.currency.checker.currencychecker.data.CryptoCurrencyList;
import com.currency.checker.currencychecker.service.exception.CurrencyException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CryptoCurrencyService extends BaseCurrencyService {

    @Autowired
    public CryptoCurrencyService(CurrencyServicesConfig config, ObjectMapper mapper) {
        super(config, mapper);
    }

    public CryptoCurrencyList getBitcoinCurrency() throws CurrencyException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(currencyServicesConfig.getBTCCurrencyURL()))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), CryptoCurrencyList.class);
            } else {
                throw new CurrencyException("Could not retrieve BTC rate.");
            }
        } catch (Exception ex) {
            throw new CurrencyException(ex.getMessage());
        }
    }

}
