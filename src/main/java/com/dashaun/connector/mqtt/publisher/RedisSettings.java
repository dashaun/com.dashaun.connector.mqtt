package com.dashaun.connector.mqtt.publisher;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Primary
@Configuration
@ConfigurationProperties(prefix = "bridge.redis")
public class RedisSettings {
    private String host;
    private int port;
    private String username;
    private String password;
}
