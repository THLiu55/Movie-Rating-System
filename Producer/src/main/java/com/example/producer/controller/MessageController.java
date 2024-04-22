package com.example.producer.controller;


import com.example.producer.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/send")
    public String sendMessageToKafka(@RequestParam String message) {
        producerService.sendMessage(message);
        return "Message sent to Kafka: " + message;
    }

    @GetMapping("/test")
    public String test() {
        return "Test";
    }
}
