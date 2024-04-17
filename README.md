# Assignment
> Group: Bug
> 
> Members: Tianhao Liu, Xiaoyu Han, Haotian Shi


## 1. Introduction
* Using Kafka as a message queue.

## Quick Start
### 1. Start Kafka Server (container)
```docker-compose up -d```

### Build maven project
```mvn clean install```

### 2. Start Consumer
```mvn exec:java -pl consumer```

### 2. Start Producer
```mvn exec:java -pl producer```