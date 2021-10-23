package com.currency.checker.currencychecker.service;

import com.currency.checker.currencychecker.config.CurrencyServicesConfig;
import com.currency.checker.currencychecker.data.CBRGeneralObject;
import com.currency.checker.currencychecker.service.exception.CurrencyException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CurrencyService extends BaseCurrencyService {

    @Autowired
    public CurrencyService(CurrencyServicesConfig config) {
        super(config);
    }

    public CBRGeneralObject getCBRCurrency() throws CurrencyException {
        ObjectMapper objectMapper = getCurrencyObjectMapper();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(currencyServicesConfig.getDefaultCurrencyURL()))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), CBRGeneralObject.class);
            } else {
                throw new CurrencyException("Could not get currency rates.");
            }
        } catch (Exception ex) {
            throw new CurrencyException(ex.getMessage());
        }
    }

}
