# Assignment

Group: Bug
Members: Tianhao Liu, Xiaoyu Han, Haotian Shi


### 1. Introduction

Using Kafka as message queue.
Using Springboot for REST API
Using Redis for caching

### Quick Start

1. Start Kafka Server (container)
```docker-compose up -d```

Build maven project
```mvn clean install```

2. Start Consumer
```java -jar consumer/target/consumer-2.6.0.jar```

3. Start Cache
```java -jar cache/target/cache-2.6.0.jar ```

4. Start Producer
```java -jar producer/target/producer-0.0.1-SNAPSHOT.jar```

5. REST APIs:
* Producer service alive test: ```localhost:8081/test``` (GET)
* Producer sending message: ```localhost:8081/send?message='test message'``` (GET)
  - After the operation, producer portal will show "Message sent to Kafka: 'test message'"
  - Consumer portal will show "Consumed message: 'test message'"

* Redis cache service alive test: ```localhost:8082/hello``` (GET)
* Put data into Redis: ```localhost:8082/set?key=test-key&value=test-value``` (POST)
* Check data in redis by key: ``` localhost:8082/get?key=test-key``` (GET)