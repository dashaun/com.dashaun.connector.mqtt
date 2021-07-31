package com.dashaun.connector.mqtt.subscriber;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Primary
@Configuration
@ConfigurationProperties(prefix = "bridge.mqtt")
public class MqttSettings {

    private String host;
    private int port;
    private String username;
    private String password;
    private String topics;
    private String clientId;

}