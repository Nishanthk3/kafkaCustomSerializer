package com.sample.kafkaCustomSerializer;

import java.util.concurrent.ExecutionException;

public interface KafkaProducerService {
    
    public boolean sendMessage(boolean async, Message message) throws InterruptedException, ExecutionException;
}
