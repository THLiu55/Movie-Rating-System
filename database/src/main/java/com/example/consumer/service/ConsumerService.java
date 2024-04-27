package com.example.consumer.service;

import com.example.consumer.model.MessageDocument;
import com.example.consumer.model.Review;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "test_topic", groupId = "test_group")
    public void consume(String message) {
        try {
            Review review = objectMapper.readValue(message, Review.class);
            System.out.println("Consumed message: " + review);

            MessageDocument messageDocument = new MessageDocument();
            messageDocument.setId(review.getId());
            messageDocument.setReview(review.getReview());
            messageDocument.setMovieId(review.getMovieId());
            mongoTemplate.save(messageDocument);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}