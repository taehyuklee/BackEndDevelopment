package com.web.crawler.cralwer.kafka.producer;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@NoArgsConstructor
public class Producer {

    @Value("${kafka.server}")
    private String kafkaServer;

    @Value("${kafka.brokerPort}")
    private String kafkaPort;

    //producer생성
    public KafkaProducer<String, String> producer;

    @PostConstruct
    public void init(){
         producer = new KafkaProducer<String, String>(getKafkaCofnig());
    }

    public Properties getKafkaCofnig() {
        String kafkaAddress = kafkaServer + ":" + kafkaPort;
        System.out.println(kafkaAddress);

        Properties configs = new Properties();
        configs.put("bootstrap.servers", kafkaAddress);
        configs.put("acks", "all");
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("block.on.buffer.full", "true");

        return configs;

    }




}
