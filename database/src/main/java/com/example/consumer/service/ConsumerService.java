package com.example.consumer.service;

import com.example.consumer.model.MessageDocument;
import com.example.consumer.model.Review;
import com.example.consumer.model.Sentiment;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "test_topic", groupId = "db_group")
    public void consume(String message) {
        try {
            Review review = objectMapper.readValue(message, Review.class);
            System.out.println("Consumed message: " + review);

            MessageDocument messageDocument = new MessageDocument();
            messageDocument.setId(review.getId());
            messageDocument.setReview(review.getReview());
            messageDocument.setMovieId(review.getMovieId());
            messageDocument.setRating("-1");
            mongoTemplate.save(messageDocument);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "result_topic", groupId = "db_group")
    public void consumeSentiment(String message) {
        try {
            Sentiment sentiment = objectMapper.readValue(message, Sentiment.class);
            System.out.println("Consumed message: " + sentiment);
            System.out.println("Sentiment:"+sentiment.getId());

            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(sentiment.getId()));
            Update update = new Update();
            update.set("rating", sentiment.getSentiment());
            mongoTemplate.findAndModify(query, update, MessageDocument.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}