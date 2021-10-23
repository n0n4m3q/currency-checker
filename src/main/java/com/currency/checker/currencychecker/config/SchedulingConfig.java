package com.currency.checker.currencychecker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Value("${app.url}")
    private String appUrl;

    public String getAppUrl() {
        return appUrl;
    }
}

