package com.sample.kafkaCustomSerializer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final static String bootstrapServers = "localhost:9092";
    private final static String stringDeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
    private final static String customDeserializer = KafkaCustomDeserializer.class.getName();
    private final static String topicName = "test2partitions";
    private final static String groupId = "groupId";

    private KafkaConsumer<String, Object> consumer = null;
    private ObjectMapper objectMapper = new ObjectMapper();

    public KafkaConsumerServiceImpl() {
        Properties consumerConfigProperties = new Properties();
        consumerConfigProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        consumerConfigProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, stringDeserializer);
        consumerConfigProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, customDeserializer);
        consumerConfigProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        consumer = new KafkaConsumer<String, Object>(consumerConfigProperties);
        consumer.subscribe(Arrays.asList(topicName));
    }

    @Override
    public <T> void consume(Consumer<T> callback) {
        Thread thread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    ConsumerRecords<String, Object> records = consumer.poll(100);
                    for (ConsumerRecord<String, Object> record : records) {
                        byte[] bytes = (byte[]) record.value();
                        callback.accept((T) objectMapper.readValue(bytes, Message.class));
                    }
                } catch (Exception ex) {
                    System.out.println("Exception caught " + ex.getMessage());
                }
                if (Thread.interrupted()) {
                    consumer.close();
                    System.out.println("After closing consumer");
                }
            }
        });

        thread.start();
        
    }

}
