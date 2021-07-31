package com.dashaun.connector.mqtt.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PublisherService {

    private final ReactiveRedisOperations<String, Object> redisTemplate;

    @Value("${topic.name:mqtt-channel}")
    private String topic;

    public PublisherService(ReactiveRedisOperations<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(Object mqttMessage) {
        log.info("Publishing: " + mqttMessage);
        this.redisTemplate
                .convertAndSend(topic, mqttMessage)
                .subscribe();
    }

}