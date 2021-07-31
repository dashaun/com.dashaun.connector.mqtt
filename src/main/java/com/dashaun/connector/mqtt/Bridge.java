package com.dashaun.connector.mqtt;

import com.dashaun.connector.mqtt.publisher.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Bridge {

    private final PublisherService publisherService;

    public Bridge(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return publisherService::publish;
    }
}
