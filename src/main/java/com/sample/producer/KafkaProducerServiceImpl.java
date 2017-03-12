package com.sample.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.custom.KafkaCustomSerializer;
import com.sample.pojo.Message;

public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final static String bootstrapServers = "localhost:9092";
    private final static String stringSerializer = "org.apache.kafka.common.serialization.StringSerializer";
    private final static String customSerializer = KafkaCustomSerializer.class.getName();
    private final static String topicName = "test";

    private Producer producer = null;
    private ObjectMapper objectMapper = new ObjectMapper();

    public KafkaProducerServiceImpl() {
        Properties producerConfigProperties = new Properties();
        producerConfigProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerConfigProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, stringSerializer);
        producerConfigProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, customSerializer);
        producer = new KafkaProducer(producerConfigProperties);
    }

    @Override
    public <T>  boolean sendMessage(boolean async, String className, Object message) throws InterruptedException, ExecutionException {
        try {
            ProducerRecord<String, Object> rec = new ProducerRecord<String, Object>(topicName, className, message);
            if (async) {
                producer.send(rec);
            } else {
                Future<RecordMetadata> future = producer.send(rec);
                System.out.println("Future,  offset : " + future.get().offset() +", topic : "+future.get().topic());
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Exception occurred while sending the message : " + ex.getMessage());
            return false;
        }
    }

}
