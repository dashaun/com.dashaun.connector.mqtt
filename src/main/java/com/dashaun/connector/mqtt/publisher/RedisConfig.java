package com.dashaun.connector.mqtt.publisher;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisSettings redis) {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redis.getHost(), redis.getPort()));
    }

    @Bean
    public ReactiveRedisOperations<String, Object> messageTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisSerializer<Object> valueSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisSerializationContext<String, Object> serializationContext =
                RedisSerializationContext.<String, Object>newSerializationContext(RedisSerializer.string())
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }
}
