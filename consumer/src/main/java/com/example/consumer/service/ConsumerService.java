package com.example.consumer.service;

import static com.mongodb.client.model.Filters.eq;
import com.example.consumer.model.MessageDocument;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);

        MessageDocument messageDocument = new MessageDocument();
        messageDocument.setMessage(message);
        mongoTemplate.save(messageDocument);
    }
}