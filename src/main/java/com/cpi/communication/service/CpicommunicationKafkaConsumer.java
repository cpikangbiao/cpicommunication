package com.cpi.communication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CpicommunicationKafkaConsumer {

    private final Logger log = LoggerFactory.getLogger(CpicommunicationKafkaConsumer.class);
    private static final String TOPIC = "topic_cpicommunication";

    @KafkaListener(topics = "topic_cpicommunication", groupId = "group_id")
    public void consume(String message) throws IOException {
        log.info("Consumed message in {} : {}", TOPIC, message);
    }
}
