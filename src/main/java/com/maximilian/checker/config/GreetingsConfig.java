package com.maximilian.checker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class GreetingsConfig {

    private final Logger logger = LoggerFactory.getLogger(GreetingsConfig.class);

    @Value("${greetings.morning}")
    private String greetingsMorning;

    @Value("${greetings.evening}")
    private String greetingsEvening;

    private Map<String, String> morningGreetingsByChatId;

    private Map<String, String> eveningGreetingsByChatId;

    public String getGreetingsMorning() {
        return greetingsMorning;
    }

    public String getGreetingsEvening() {
        return greetingsEvening;
    }

    public Map<String, String> getMorningGreetingByChatIdMap() {
        synchronized (this) {
            if(morningGreetingsByChatId == null) {
                morningGreetingsByChatId = getGreetingByChatIdMap(greetingsMorning);
            }
        }
        return new HashMap<>(morningGreetingsByChatId);
    }

    public Map<String, String> getEveningGreetingByChatIdMap() {
        synchronized (this) {
            if(eveningGreetingsByChatId == null) {
                eveningGreetingsByChatId = getGreetingByChatIdMap(greetingsEvening);
            }
        }
        return new HashMap<>(eveningGreetingsByChatId);
    }

    private Map<String, String> getGreetingByChatIdMap(String greetingLine) {
        String[] split = greetingLine.split("\\|");
        Map<String, String> result = new HashMap<>();
        for(String pair : split) {
            String[] pairSplit = pair.split(":");
            if(pairSplit.length > 1) {
                result.put(pairSplit[0], pairSplit[1]);
            } else {
                logger.warn("Invalid pair '" + pair + "' to parse");
            }
        }
        logger.info(result.toString());
        return result;
    }
}
