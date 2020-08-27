package com.example.logproducer.controllers.api;

import com.example.logproducer.controllers.request.LogRequest;
import com.example.logproducer.kafka.LogProducer;
import com.example.logproducer.models.domain.Log;
import com.example.logproducer.models.statistics.Behavior;
import com.example.logproducer.services.LogService;
import com.example.logproducer.models.statistics.Statistic;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@CommonsLog(topic = "Log Controller")
public class LogController {

    @Autowired
    private LogProducer logProducer;

    @Autowired
    private LogService logService;

    @GetMapping
    Page<Log> getLogs(Pageable pageable){
        return logService.getLogs(pageable);
    }

    @GetMapping("/statistics")
    List<Behavior> getStatistics(@RequestParam("days") Integer days){
        return logService.getBehaviorsInLastDays(days);
    }

    @PostMapping
    LogRequest sendLog(@RequestBody LogRequest logRequest, HttpServletRequest request){
//        System.out.println(logRequest.getEventName());
//        System.out.println(System.currentTimeMillis());
        if (logRequest == null){
            logRequest.setTime(new Date());
        }
//        logRequest.setIp(request.getRemoteAddr());
//        logRequest.setPlatform(Platform.getPlatform(request.getHeader("User-Agent")));

        log.warn("push log...");
        logProducer.pushLog(logRequest);
        return logRequest;
    }

//    @GetMapping
//    String getHeaders(@RequestHeader Map<String, String> headers, HttpServletRequest request){
//        log.warn("------------------");
//        headers.forEach((key, value) -> {
//            log.warn(key + ": " + value);
//        });
//        log.warn("Client IP: " + request.getRemoteAddr());
//        log.warn("User Agent: " + request.getHeader("User-Agent"));
//        log.warn("Client Platform: " + Platform.getPlatform(request.getHeader("User-Agent")));
//
//        log.warn("------------------");
//
//        return "hello";
//    }
}
