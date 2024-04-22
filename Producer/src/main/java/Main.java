import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        // set the properties for the Kafka producer
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // set the key and value serializer classes
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        // create a Kafka producer object
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        // create a JSON object
        JSONObject data = new JSONObject();
        Random random = new Random();
        int id = random.nextInt(1000);
        int id1 = random.nextInt(1000);
        data.put("id", id1);
        data.put("username", "sky");
        data.put("goodsname", "package");

        producer.send(new ProducerRecord<>("test-topic1", String.valueOf(id) , data.toString()));


        // close the producer
        producer.close();
    }
}
