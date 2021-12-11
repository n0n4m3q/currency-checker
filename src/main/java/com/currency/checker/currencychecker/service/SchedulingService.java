package com.currency.checker.currencychecker.service;

import com.currency.checker.currencychecker.bot.CurrencyBot;
import com.currency.checker.currencychecker.config.GreetingsConfig;
import com.currency.checker.currencychecker.config.SchedulingConfig;
import com.currency.checker.currencychecker.config.TelegramConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SchedulingService {

    private final CurrencyBot currencyBot;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Logger logger = LoggerFactory.getLogger(SchedulingService.class);
    private final TelegramConfig telegramConfig;
    private final GreetingsConfig greetingsConfig;
    private final SchedulingConfig schedulingConfig;

    @Autowired
    public SchedulingService(CurrencyBot currencyBot, TelegramConfig telegramConfig, GreetingsConfig greetingsConfig, SchedulingConfig schedulingConfig) {
        this.currencyBot = currencyBot;
        this.telegramConfig = telegramConfig;
        this.greetingsConfig = greetingsConfig;
        this.schedulingConfig = schedulingConfig;
    }

    // 10:00 every day
    @Scheduled(cron = "0 0 10 * * *", zone = "Europe/Moscow")
    private void notifyWithCurrenciesMorning() {
        currencyBot.sendCurrenciesTo(telegramConfig.getGeneralChatId(), greetingsConfig.getGreetingsMorning() + "\n");
    }

    // 22:00 every day
    @Scheduled(cron = "0 0 22 * * *", zone = "Europe/Moscow")
    private void notifyWithCurrenciesEvening() {
        currencyBot.sendCurrenciesTo(telegramConfig.getGeneralChatId(), greetingsConfig.getGreetingsEvening() + "\n");
    }

    // is used to prevent heroku from putting application to sleep mode
    @Scheduled(cron = "0 */5 * * * *", zone = "Europe/Moscow")
    private void pingSelf() {
        ResponseEntity<String> response = restTemplate.getForEntity(schedulingConfig.getAppUrl(), String.class);
        logger.info("Heartbeat response: " + response.getBody());
    }


}
