package com.maximilian.checker.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/heartbeat")
public class RestService {

    @GetMapping
    public ResponseEntity<String> getHeartBeat() throws InterruptedException {
        Thread.sleep(7500);
        return ResponseEntity.ok("Alive");
    }

}
