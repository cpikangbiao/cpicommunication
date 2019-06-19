package com.cpi.communication.web.rest;

import com.cpi.communication.service.CpicommunicationKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cpicommunication-kafka")
public class CpicommunicationKafkaResource {

    private final Logger log = LoggerFactory.getLogger(CpicommunicationKafkaResource.class);

    private CpicommunicationKafkaProducer kafkaProducer;

    public CpicommunicationKafkaResource(CpicommunicationKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.sendMessage(message);
    }
}
