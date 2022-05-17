package com.maximilian.checker.service;

import com.maximilian.checker.config.CurrencyServicesConfig;
import com.maximilian.checker.data.CBRGeneralObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService extends BaseCurrencyService {

    @Autowired
    protected CurrencyService(CurrencyServicesConfig currencyServicesConfig, HttpClientWrapper httpClientWrapper) {
        super(currencyServicesConfig, httpClientWrapper);
    }

    public CBRGeneralObject getCBRCurrency() {
        return httpClientWrapper.getFromUrl(currencyServicesConfig.getDefaultCurrencyURL(), CBRGeneralObject.class);
    }

}
