package com.currency.checker.currencychecker.bot;

import com.currency.checker.currencychecker.config.TelegramConfig;
import com.currency.checker.currencychecker.data.CBRCurrency;
import com.currency.checker.currencychecker.data.CBRGeneralObject;
import com.currency.checker.currencychecker.data.CryptoCurrencyList;
import com.currency.checker.currencychecker.service.CryptoCurrencyService;
import com.currency.checker.currencychecker.service.CurrencyService;
import com.currency.checker.currencychecker.service.exception.CurrencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Component
public class CurrencyBot extends TelegramLongPollingBot {

    private static final String RUSSIA_CODE = "\uD83C\uDDF7\uD83C\uDDFA";
    private static final String USA_CODE = "\uD83C\uDDFA\uD83C\uDDF8";
    private static final String EUR_CODE = "\uD83C\uDDEA\uD83C\uDDFA";
    private final CryptoCurrencyService cryptoCurrencyService;
    private final CurrencyService currencyService;
    private final TelegramConfig telegramConfig;

    private static final Logger logger = Logger.getGlobal();

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals(telegramConfig.getPassword())) {
                String chatId = String.valueOf(update.getMessage().getChatId());
                sendCurrenciesTo(chatId, "Hello, currency rates for today : \n");
            }
        }
    }

    public void sendMessageTo(String message, String chatId) {
        SendMessage msg = new SendMessage();
        msg.setText(message);
        msg.setChatId(chatId);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendCurrenciesTo(String chatId, String greetings) {
        String cryptoMsg;
        String cbrMsg;
        try {
            cryptoMsg = getCryptoMessage(cryptoCurrencyService.getBitcoinCurrency());
        } catch (CurrencyException ex) {
            logger.warning(ex.getMessage());
            cryptoMsg = "Unable to get Bitcoin rate.";
        }
        try {
            cbrMsg = getCurrencyMessage(currencyService.getCBRCurrency());
        } catch (CurrencyException ex) {
            logger.warning(ex.getMessage());
            cbrMsg = "Unable to get currency rates.";
        }
        sendMessageTo(greetings + "Today is " + getCurrentDateString() + ", currency rates: " + "\n\n" +
                cryptoMsg + "\n\n" + cbrMsg, chatId);
    }

    private String getCryptoMessage(CryptoCurrencyList list) {
        return "BTC price(buy price) in '" + list.getUSD().getSymbol() + "' " + USA_CODE + ": " + list.getUSD().getBuy() + " $";
    }

    private String getCurrencyMessage(CBRGeneralObject currencyObj) {
        CBRCurrency usd = currencyObj.getValute().getUSD();
        CBRCurrency eur = currencyObj.getValute().getEUR();
        return EUR_CODE + ": price for " + eur.getNominal() + " " + eur.getCharcode() + " is " + eur.getValue() + " RUB " + RUSSIA_CODE + "\n" +
                USA_CODE + ": price for " + usd.getNominal() + " " + usd.getCharcode() + " is " + usd.getValue() + " RUB " + RUSSIA_CODE;
    }

    private String getCurrentDateString() {
        try {
            return ZonedDateTime.now(ZoneId.of(telegramConfig.getDesiredZoneId())).format(DateTimeFormatter.RFC_1123_DATE_TIME);
        } catch (DateTimeException ex) {
            logger.warning("Invalid zone id [" + telegramConfig.getDesiredZoneId() + "]");
            return ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.RFC_1123_DATE_TIME);
        }
    }

}

