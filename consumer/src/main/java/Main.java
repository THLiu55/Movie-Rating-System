import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        //set the properties for the Kafka consumer
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // deserializer classes
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // group id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");

        // create a Kafka consumer object
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // subscribe to the Kafka topic
        consumer.subscribe(Collections.singletonList("test-topic1"));
        // connect to the MySQL server
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kafka?useSSL=false&serverTimezone=UTC", "root", "shihaotian2002")) {
            System.out.println("Connected to the MySQL server successfully.");
            String sql = "INSERT INTO events (id, username, goodsname) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            while (true) {
                final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    JSONObject data = new JSONObject(record.value());
                    int id = data.getInt("id");
                    String username = data.getString("username");
                    String goodsname = data.getString("goodsname");
                    System.out.println("id: " + id + ", username: " + username + ", goodsname: " + goodsname);
                    statement.setInt(1, data.getInt("id"));
                    statement.setString(2, data.getString("username"));
                    statement.setString(3, data.getString("goodsname"));
                    statement.executeUpdate();


                }
            }
        } catch (Exception e) {
            System.out.println("Error connecting to the MySQL server.");
            e.printStackTrace();


        }
    }
}
