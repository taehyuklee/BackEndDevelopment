package com.web.crawler.cralwer.kafka.consumer;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@NoArgsConstructor
public class Consumer {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.brokerPort}")
    private String kafkaPort;

    public KafkaConsumer<String, String> consumer;

    @PostConstruct
    public void  init(){
        consumer = new KafkaConsumer<String, String>(getKafkaCofnig());
    }

//    @KafkaListener(
//            topics = "kafka-test",
//            groupId = "kafka"
//    )
//    public void listen(String msg) throws IOException {
//        System.out.println(String.format("Consumed message : %s", msg));
//    }

    public Properties getKafkaCofnig() {
        String kafkaAddress = kafkaServer + ":" + kafkaPort;
        System.out.println(kafkaAddress);

        Properties configs = new Properties();
        configs.put("bootstrap.servers", kafkaAddress);
        configs.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        configs.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        configs.put("group.id", "quickstart-events");

        return configs;

    }
}
