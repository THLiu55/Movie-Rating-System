package com.example.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String getValue(String key) {
        String res = (String) redisTemplate.opsForValue().get(key);
        if (res == null) {
            return "-1, -1";
        }
        return res;
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/getRatings")
    public List<Float> getRatings() {

        List<Float> ratingList = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String movie_val = getValue(Integer.toString(i));
            if (movie_val.equals("-1, -1")) {
                ratingList.add(0.0f);
            } else {
                String[] values = movie_val.split(", ");
                int sentiment = Integer.parseInt(values[0]);
                int count = Integer.parseInt(values[1]);
                ratingList.add((float) sentiment / count);
            }
        }
        return ratingList;
    }


    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }
}
