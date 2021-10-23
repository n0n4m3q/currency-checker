package com.currency.checker.currencychecker.service;

import com.currency.checker.currencychecker.config.CurrencyServicesConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.time.Duration;

public abstract class BaseCurrencyService {

    protected final CurrencyServicesConfig currencyServicesConfig;

    protected final HttpClient httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(Duration.ofMinutes(2))
            .build();

    protected BaseCurrencyService(CurrencyServicesConfig currencyServicesConfig) {
        this.currencyServicesConfig = currencyServicesConfig;
    }

    protected ObjectMapper getCurrencyObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        return objectMapper;
    }

}
