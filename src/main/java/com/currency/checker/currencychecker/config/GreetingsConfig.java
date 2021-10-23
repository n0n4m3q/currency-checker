package com.currency.checker.currencychecker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingsConfig {

    @Value("${greetings.morning}")
    private String greetingsMorning;

    @Value("${greetings.evening}")
    private String greetingsEvening;

    public String getGreetingsMorning() {
        return greetingsMorning;
    }

    public String getGreetingsEvening() {
        return greetingsEvening;
    }
}
