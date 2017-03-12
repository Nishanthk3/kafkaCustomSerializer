package com.sample.consumer;

import java.util.function.Consumer;
import java.util.function.Function;

import com.sample.pojo.DeciderEnum;

public interface KafkaConsumerService {

    <T> void consume(Consumer<T> callback );
    <T> void consume(Consumer<T> callback , Function<T,DeciderEnum> decider);
}
