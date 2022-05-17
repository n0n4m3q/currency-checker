package com.maximilian.checker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class TelegramConfig {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Value("${access.pwd}")
    private String password;

    @Value("${zone.id}")
    private String desiredZoneId;

    @Value("${general.ids}")
    private String chatIds;

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

    public List<String> getChatIds() {
        return Arrays.asList(chatIds.split(","));
    }
}
