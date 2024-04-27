from kafka import KafkaConsumer, KafkaProducer
import json
from service.model import Classifier
from os import environ

# Kafka 服务器地址, the ip address of the kafka server is 'kafka' docker container
bootstrap_servers = ['localhost:29092']

# 主题名称
topic = 'test_topic'

classifier = Classifier(model_path='model/sentiment_analysis_model.pkl', tfidf_path='model/tfidf_vectorizer.pkl')

# 创建 Kafka 消费者
print(f"Connecting to Kafka server at {bootstrap_servers}")
consumer = KafkaConsumer(topic,
                         group_id='test_group',
                         bootstrap_servers=bootstrap_servers,
                         auto_offset_reset='earliest')

print("Connected to Kafka server")

producer = KafkaProducer(bootstrap_servers=bootstrap_servers)

# 消费消息
try:
    for message in consumer:
        json_message = json.loads(message.value.decode('utf-8'))
        sentiment = classifier.predict(json_message['review'])
        print(f"Predict result of review_{json_message['id']} is {sentiment}")
        producer.send('result_topic', json.dumps({'id': json_message['id'], 'movie_id': json_message['movie_id'] ,'sentiment': int(sentiment)}).encode('utf-8'))
except KeyboardInterrupt:
    # 在接收到键盘中断（Ctrl+C）时关闭消费者连接
    consumer.close()
