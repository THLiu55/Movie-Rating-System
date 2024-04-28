package com.example.consumer.service;

import com.example.consumer.model.MessageDocument;
import com.example.consumer.model.Review;
import com.example.consumer.model.Sentiment;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            System.out.println("Sentiment:" + sentiment.getId());

            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(sentiment.getId()));
            Update update = new Update();
            update.set("rating", sentiment.getSentiment());
            mongoTemplate.findAndModify(query, update, MessageDocument.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Page<MessageDocument> getAllMessages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Query query = new Query();
        query.with(pageable);

        List<MessageDocument> messages = mongoTemplate.find(query, MessageDocument.class);

        Query countQuery = new Query();
        long total = mongoTemplate.count(countQuery, MessageDocument.class);

        return new PageImpl<>(messages, pageable, total);
    }

    public Map<String, Long> getReviewCountPerMovie() {
        List<MessageDocument> messages = mongoTemplate.findAll(MessageDocument.class);
        return messages.stream()
                .collect(Collectors.groupingBy(MessageDocument::getMovieId, Collectors.counting()));
    }

    public Pair<Long, Double> getReviewCountAndPositiveRate(String movieId) {
        List<MessageDocument> messages = mongoTemplate.find(Query.query(Criteria.where("movieId").is(movieId)), MessageDocument.class);
        long count = messages.size();
        double positiveRate = messages.stream().filter(m -> Double.parseDouble(m.getRating()) > 0.5).count() / (double) count;
        return Pair.of(count, positiveRate);
    }
}