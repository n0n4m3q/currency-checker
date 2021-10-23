package com.currency.checker.currencychecker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:telegram.properties")
public class TelegramConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Value("${access.pwd}")
    private String password;

    @Value("${zone.id}")
    private String desiredZoneId;

    @Value("${general.id}")
    private String generalChatId;

    public String getDesiredZoneId() {
        return desiredZoneId;
    }

    public String getPassword() {
        return password;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotName() {
        return botName;
    }

    public String getGeneralChatId() {
        return generalChatId;
    }
}
