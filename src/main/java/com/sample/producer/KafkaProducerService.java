package com.sample.producer;

import java.util.concurrent.ExecutionException;

import com.sample.pojo.Message;

public interface KafkaProducerService {
    
	<T> boolean sendMessage(boolean async, String className, Object message) throws InterruptedException, ExecutionException;
}
