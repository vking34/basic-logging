package com.example.logproducer.utils;

import com.example.logproducer.controllers.request.LogRequest;
import com.example.logproducer.kafka.LogProducer;
import com.example.logproducer.models.domain.Platform;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@CommonsLog(topic = "Log Generator")
public class LogGeneratorThread implements Runnable {

    private LogProducer logProducer;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    private static String[] EVENT_NAMES = {"click", "impression"};
    private static String[] IP_ADDRESSES = {"19.16.2.3", "222.168.2.2", "12.148.22.3"};
    private static String[] CITIES = {"Hanoi", "Ho Chi Minh", "Da Nang"};

    public LogGeneratorThread(LogProducer logProducer) {
        this.logProducer = logProducer;
    }

    @SneakyThrows
    @Override
    public void run() {
        LogRequest logRequest = new LogRequest();
        logRequest.setCountry("Vietnam");
        Random randomGenerator = new Random();

        for (int i = 0; i < 20000; i++) {
            for(int j = 0; j < 50; j++) {
                int eventId = randomGenerator.nextInt(EVENT_NAMES.length);
                logRequest.setEventName(EVENT_NAMES[eventId]);
                int userId = randomGenerator.nextInt(10000);
                logRequest.setUserId(userId);
                int shopId = randomGenerator.nextInt(10000);
                logRequest.setShopId(shopId);
                int platformId = randomGenerator.nextInt(Platform.values().length);
                logRequest.setPlatform(Platform.values()[platformId]);
                int ipOffset = randomGenerator.nextInt(IP_ADDRESSES.length);
                logRequest.setIp(IP_ADDRESSES[ipOffset]);
                int cityId = randomGenerator.nextInt(CITIES.length);
                logRequest.setCity(CITIES[cityId]);
                long aDay = TimeUnit.DAYS.toMillis(1);
                long now = new Date().getTime();
                long hundredDaysAgo = new Date(now - aDay * 100).getTime();
                Date randomDate = new Date(ThreadLocalRandom.current().nextLong(hundredDaysAgo, now));
                logRequest.setTime(randomDate);
//            log.warn(objectWriter.writeValueAsString(logRequest));

            logProducer.pushLog(logRequest);
            }
            Thread.sleep(5000);
            log.warn("inserting time: " + i);
        }
        log.warn("DONE!");
    }
}
