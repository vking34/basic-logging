package com.example.logproducer.kafka;

import com.example.logproducer.models.Log;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@CommonsLog(topic = "Producer Logger")
public class LogProducer {
    @Value("${topic.name}")
    private String TOPIC;

    @Autowired
    private KafkaTemplate<String, Log> kafkaTemplate;

    public void pushLog(Log log){
        this.kafkaTemplate.send(this.TOPIC, log.getEventName(), log);
    }
}
