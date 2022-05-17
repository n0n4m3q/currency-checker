package com.maximilian.checker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maximilian.checker.service.exception.GeneralException;
import com.maximilian.checker.service.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class HttpClientWrapper {

    protected final HttpClient httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(Duration.ofMinutes(2))
            .build();
    protected final ObjectMapper objectMapper;

    @Autowired
    public HttpClientWrapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T getFromUrl(String url, Class<T> clazz) {
        try {
            HttpResponse<String> response = getResponseFromUrl(url, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpStatus.OK.value()) {
                throw new HttpException("Could not retrieve response from " + url + ", status code is " + response.statusCode());
            }
            return objectMapper.readValue(response.body(), clazz);
        } catch (Exception ex) {
            throw new GeneralException(ex.getMessage());
        }
    }

    public <T> HttpResponse<T> getResponseFromUrl(String url, HttpResponse.BodyHandler<T> bodyHandler) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        return httpClient.send(request, bodyHandler);
    }

}
