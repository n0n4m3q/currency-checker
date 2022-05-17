package com.maximilian.checker.bot;

import com.maximilian.checker.config.TelegramConfig;
import com.maximilian.checker.data.CBRCurrency;
import com.maximilian.checker.data.CBRGeneralObject;
import com.maximilian.checker.data.CryptoCurrencyList;
import com.maximilian.checker.service.CryptoCurrencyService;
import com.maximilian.checker.service.CurrencyService;
import com.maximilian.checker.service.exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class CurrencyBot extends TelegramLongPollingBot {

    private static final String RUSSIA_FLAG_CODE = "\uD83C\uDDF7\uD83C\uDDFA";
    private static final String USA_FLAG_CODE = "\uD83C\uDDFA\uD83C\uDDF8";
    private static final String EUR_FLAG_CODE = "\uD83C\uDDEA\uD83C\uDDFA";

    private static final String CURRENCY_MESSAGE = "%s\nToday is %s, currency rates: \n\n%s\n\n%s";
    private static final String DEFAULT_GREETINGS = "Hello there!";

    private static final Logger logger = LoggerFactory.getLogger(CurrencyBot.class);

    private final CryptoCurrencyService cryptoCurrencyService;
    private final CurrencyService currencyService;
    private final TelegramConfig telegramConfig;

    @Autowired
    public CurrencyBot(CryptoCurrencyService cryptoCurrencyService, CurrencyService currencyService, TelegramConfig telegramConfig) {
        this.cryptoCurrencyService = cryptoCurrencyService;
        this.currencyService = currencyService;
        this.telegramConfig = telegramConfig;
    }

    @Override
    public String getBotUsername() {
        return telegramConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return telegramConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() &&
                update.getMessage().hasText() &&
                telegramConfig.getPassword().equals(update.getMessage().getText())) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            sendCurrenciesTo(List.of(chatId), Map.of(chatId, DEFAULT_GREETINGS));
        }
    }

    public void sendMessageTo(String message, String chatId) {
        try {
            SendMessage msg = SendMessage.builder().chatId(chatId).text(message).build();
            executeAsync(msg);
        } catch (TelegramApiException ex) {
            logger.error("Error occurred while trying to send message to " + chatId + ". Error '" + ex.getMessage() + "'.");
        }
    }

    public void sendCurrenciesTo(List<String> chatIds, Map<String, String> greetingsByChatIdMap) {
        String cryptoMsg;
        String cbrMsg;
        try {
            cryptoMsg = getCryptoMessage(cryptoCurrencyService.getBitcoinCurrency());
        } catch (HttpException ex) {
            logger.error(ex.getMessage());
            cryptoMsg = "Unable to get Bitcoin rate.";
        }
        try {
            cbrMsg = getCurrencyMessage(currencyService.getCBRCurrency());
        } catch (HttpException ex) {
            logger.error(ex.getMessage());
            cbrMsg = "Unable to get currency rates.";
        }
        for (String chatId : chatIds) {
            sendMessageTo(
                    String.format(
                            CURRENCY_MESSAGE,
                            greetingsByChatIdMap.getOrDefault(chatId, DEFAULT_GREETINGS),
                            getCurrentDateString(),
                            cryptoMsg,
                            cbrMsg
                    ),
                    chatId
            );
        }
    }

    private String getCryptoMessage(CryptoCurrencyList list) {
        return "BTC price(buy price) in '" + list.getUSD().getSymbol() + "' " + USA_FLAG_CODE + ": " + list.getUSD().getBuy() + " $";
    }

    private String getCurrencyMessage(CBRGeneralObject currencyObj) {
        CBRCurrency usd = currencyObj.getValute().getUSD();
        CBRCurrency eur = currencyObj.getValute().getEUR();
        return EUR_FLAG_CODE + ": price for " + eur.getNominal() + " " + eur.getCharcode() + " is " + eur.getValue() + " RUB " + RUSSIA_FLAG_CODE + "\n" +
                USA_FLAG_CODE + ": price for " + usd.getNominal() + " " + usd.getCharcode() + " is " + usd.getValue() + " RUB " + RUSSIA_FLAG_CODE;
    }

    private String getCurrentDateString() {
        boolean zoneExists = ZoneId.getAvailableZoneIds().stream().anyMatch(telegramConfig.getDesiredZoneId()::equals);
        ZoneId zone = ZoneId.systemDefault();
        if (zoneExists) {
            zone = ZoneId.of(telegramConfig.getDesiredZoneId());
        }
        return ZonedDateTime.now(zone).format(DateTimeFormatter.RFC_1123_DATE_TIME);
    }

}

