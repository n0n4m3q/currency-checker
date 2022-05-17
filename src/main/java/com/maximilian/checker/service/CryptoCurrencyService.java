package com.maximilian.checker.service;

import com.maximilian.checker.config.CurrencyServicesConfig;
import com.maximilian.checker.data.CryptoCurrencyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoCurrencyService extends BaseCurrencyService {

    @Autowired
    protected CryptoCurrencyService(CurrencyServicesConfig currencyServicesConfig, HttpClientWrapper httpClientWrapper) {
        super(currencyServicesConfig, httpClientWrapper);
    }

    public CryptoCurrencyList getBitcoinCurrency() {
        return httpClientWrapper.getFromUrl(currencyServicesConfig.getBTCCurrencyURL(), CryptoCurrencyList.class);
    }

}
