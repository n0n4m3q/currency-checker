package com.currency.checker.currencychecker.service;

import com.currency.checker.currencychecker.config.CurrencyServicesConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.time.Duration;

public abstract class BaseCurrencyService {

    protected final CurrencyServicesConfig currencyServicesConfig;
    protected final ObjectMapper objectMapper;

    protected final HttpClient httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(Duration.ofMinutes(2))
            .build();

    protected BaseCurrencyService(CurrencyServicesConfig currencyServicesConfig, ObjectMapper objectMapper) {
        this.currencyServicesConfig = currencyServicesConfig;
        this.objectMapper = objectMapper;
    }

}
