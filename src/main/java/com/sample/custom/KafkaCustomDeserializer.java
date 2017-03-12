package com.sample.custom;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

public class KafkaCustomDeserializer<Object> implements Deserializer<Object>{

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Object deserialize(String topic, byte[] data) {
        // TODO Auto-generated method stub
        return (Object) data;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }

}
