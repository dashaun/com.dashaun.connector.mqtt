package com.dashaun.connector.mqtt.subscriber;

import com.dashaun.connector.mqtt.publisher.RedisSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.event.MqttConnectionFailedEvent;
import org.springframework.integration.mqtt.event.MqttSubscribedEvent;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableConfigurationProperties({
        MqttSettings.class,
        RedisSettings.class
})
@Slf4j
public class MqttConfig {

    @EventListener
    public void MqttSubscribedEventListener(MqttSubscribedEvent mqttSubscribedEvent) {
        log.info(mqttSubscribedEvent.getMessage());
    }

    @EventListener
    public void MqttConnectionFailedEventListener(MqttConnectionFailedEvent mqttConnectionFailedEvent) {
        log.error("MQTT Connection Failed: " + mqttConnectionFailedEvent.getTimestamp());
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound(MqttSettings mqtt) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        String.format("tcp://%s:%s", mqtt.getHost(), mqtt.getPort()),
                        mqtt.getClientId(),
                        mqtt.getTopics().split(","));
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

}