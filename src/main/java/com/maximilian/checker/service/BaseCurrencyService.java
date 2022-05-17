package com.maximilian.checker.service;

import com.maximilian.checker.config.CurrencyServicesConfig;

public abstract class BaseCurrencyService {

    protected final CurrencyServicesConfig currencyServicesConfig;
    protected final HttpClientWrapper httpClientWrapper;

    protected BaseCurrencyService(CurrencyServicesConfig currencyServicesConfig, HttpClientWrapper httpClientWrapper) {
        this.currencyServicesConfig = currencyServicesConfig;
        this.httpClientWrapper = httpClientWrapper;
    }

}
