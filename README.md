# Assignment

Group: Bug
Members: Tianhao Liu, Xiaoyu Han, Haotian Shi


## Introduction

Using Kafka as message queue.
Using Springboot for REST API
Using Redis for caching


## Quick Start

1. Start Kafka Server (container)
```docker-compose up -d```

Build maven project
```mvn clean install```

2. Start Consumer
move to the consumer folder
```cd consumer```
execute the start.sh script or use the terminal to execute
```docker build -t consumer .  ```
```docker run -it --name consumer --network host consumer```

3. Start Database module
move to project folder and run the jar
```java -jar database/target/database-2.6.0.jar```

4. Start Cache
```java -jar cache/target/cache-2.6.0.jar ```

5. Start Producer
move to the producer folder
```cd producer```
execute the start.sh script or use the terminal to execute
```docker build -t producer .  ```
```docker run -it --name producer --network host producer```

## Database functions
* Go to localhost:8080/messages to view the database ans the reviews
* Go to localhost:8080/search to search by move id(1,2,3) and view the total reviews and rate of positive ratings