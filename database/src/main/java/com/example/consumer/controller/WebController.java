package com.example.consumer.controller;

import com.example.consumer.model.MessageDocument;
import com.example.consumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/messages")
    public String getMessages(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        Page<MessageDocument> messagesPage = consumerService.getAllMessages(page, size);
        model.addAttribute("messagesPage", messagesPage);
        return "messages";
    }



    @GetMapping("/search")
    public String search(@RequestParam(required = false) String movieId, Model model) {
        if (movieId != null) {
            Pair<Long, Double> reviewCountAndPositiveRate = consumerService.getReviewCountAndPositiveRate(movieId);
            model.addAttribute("movieId", movieId);
            model.addAttribute("reviewCount", reviewCountAndPositiveRate.getFirst());
            model.addAttribute("positiveRate", reviewCountAndPositiveRate.getSecond());
        }
        return "search";
    }
}
