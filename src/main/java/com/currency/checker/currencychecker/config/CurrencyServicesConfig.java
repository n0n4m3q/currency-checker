package com.currency.checker.currencychecker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyServicesConfig {

    @Value("${currency.btc.url}")
    private String btcCurrencyUrl;

    @Value("${currency.default.url}")
    private String defaultCurrencyUrl;

    public String getBTCCurrencyURL() {
        return btcCurrencyUrl;
    }

    public String getDefaultCurrencyURL() {
        return defaultCurrencyUrl;
    }

}
