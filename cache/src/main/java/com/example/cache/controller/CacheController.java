package com.example.cache.controller;

import com.example.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {

    @Autowired
    private CacheService redisService;

    @GetMapping("/hello")
    public String test() {
        return "Hello, this is redis cache!";
    }

    @PostMapping("/set")
    public String setValue(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return "Value set successfully!";
    }

    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        String value = redisService.getValue(key);
        return "Value retrieved from Redis: " + value;
    }

}
