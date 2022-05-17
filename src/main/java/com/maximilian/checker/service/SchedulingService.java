package com.maximilian.checker.service;

import com.maximilian.checker.bot.CurrencyBot;
import com.maximilian.checker.config.GreetingsConfig;
import com.maximilian.checker.config.SchedulingConfig;
import com.maximilian.checker.config.TelegramConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.http.HttpResponse;

@Service
public class SchedulingService {

    private static final Logger logger = LoggerFactory.getLogger(SchedulingService.class);

    private final CurrencyBot currencyBot;
    private final HttpClientWrapper httpClientWrapper;
    private final TelegramConfig telegramConfig;
    private final GreetingsConfig greetingsConfig;
    private final SchedulingConfig schedulingConfig;

    @Autowired
    public SchedulingService(CurrencyBot currencyBot, HttpClientWrapper httpClientWrapper, TelegramConfig telegramConfig, GreetingsConfig greetingsConfig, SchedulingConfig schedulingConfig) {
        this.currencyBot = currencyBot;
        this.httpClientWrapper = httpClientWrapper;
        this.telegramConfig = telegramConfig;
        this.greetingsConfig = greetingsConfig;
        this.schedulingConfig = schedulingConfig;
    }

    // 10:00 every day
    @Scheduled(cron = "0 0 10 * * *", zone = "Europe/Moscow")
    private void notifyWithCurrenciesMorning() {
        currencyBot.sendCurrenciesTo(telegramConfig.getChatIds(), greetingsConfig.getMorningGreetingByChatIdMap());
    }

    // 22:00 every day
    @Scheduled(cron = "0 0 22 * * *", zone = "Europe/Moscow")
    private void notifyWithCurrenciesEvening() {
        currencyBot.sendCurrenciesTo(telegramConfig.getChatIds(), greetingsConfig.getEveningGreetingByChatIdMap());
    }

    // every 5 minutes, is used to prevent heroku from putting application to sleep mode,
    @Scheduled(cron = "0 */5 * * * *", zone = "Europe/Moscow")
    private void pingSelf() {
        try {
            HttpResponse<String> responseFromUrl = httpClientWrapper.getResponseFromUrl(schedulingConfig.getAppUrl(), HttpResponse.BodyHandlers.ofString());
            logger.info("Heartbeat response: " + responseFromUrl.body());
        } catch (Exception ex) {
            logger.warn("Error while requesting heartbeat: " + ex.getMessage());
        }
    }

}
