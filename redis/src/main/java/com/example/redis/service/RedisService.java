package com.example.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        String res = (String) redisTemplate.opsForValue().get(key);
        if (res == null) {
            return "-1, -1";
        }
        return res;
    }

    @KafkaListener(topics = "result_topic", groupId = "redis-group")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(message);
            String movieId = jsonNode.get("movie_id").asText();
            Integer sentiment = jsonNode.get("sentiment").asInt();
            String currentValue = getValue(movieId);
            if (currentValue.equals("-1, -1")) {
                setValue(movieId, sentiment + ", 1");
            } else {
                String[] values = currentValue.split(", ");
                int currentSentiment = Integer.parseInt(values[0]);
                int currentCount = Integer.parseInt(values[1]);
                setValue(movieId, currentSentiment + sentiment + ", " + (currentCount + 1));
                System.out.println("Updated value: " + (currentSentiment + sentiment) + ", " + (currentCount + 1));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
