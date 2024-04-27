from kafka import KafkaProducer
import json
import time
import random

# Kafka 服务器地址
bootstrap_servers = ['localhost:29092']

# 创建 Kafka 生产者
producer = KafkaProducer(bootstrap_servers=bootstrap_servers)

topic = 'test_topic'

with open('IMDB Dataset1.json') as f:
    dataset = json.load(f)

# 发送消息到 Kafka
for i in range(len(dataset)):
    message = dataset[i]
    message['id'] = f'producer_{i}'
    producer.send(topic, json.dumps(message).encode('utf-8'))
    time.sleep(random.uniform(0.3, 0.5))
    print(f"Sent message {i}")

# 关闭生产者连接
producer.close()
