package com.currency.checker.currencychecker;

import com.currency.checker.currencychecker.bot.CurrencyBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyCheckerApplication {

    @Autowired
    CurrencyBot currencyBot;

    public static void main(String[] args) {
        SpringApplication.run(CurrencyCheckerApplication.class, args);

    }

}
