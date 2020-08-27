package com.example.logproducer.utils;

import com.example.logproducer.kafka.LogProducer;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@CommonsLog(topic = "Log Generator")
@Component
public class LogGenerator {

    @Autowired
    private LogProducer logProducer;

//    @Bean
    public void generateRandomLogs() {
//        log.warn("Generate random logs...");
        Thread thread;
        LogGeneratorThread generatorThread = new LogGeneratorThread(logProducer);
        thread = new Thread(generatorThread);
        thread.setName("log_generator");
        thread.setDaemon(false);
        thread.start();
    }
}
