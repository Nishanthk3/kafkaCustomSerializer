package com.sample.kafkaCustomSerializer;

import java.util.function.Consumer;

public interface KafkaConsumerService {

    public <T> void consume(Consumer<T> callback );
}
