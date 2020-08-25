package com.example.logproducer;

import lombok.extern.apachecommons.CommonsLog;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/logs")
@CommonsLog(topic = "Log Controller")
public class LogController {

    @Autowired
    private LogProducer logProducer;

    @Autowired
    private LogService logService;

    @PostMapping
    Log sendLog(@RequestBody Log logRequest, HttpServletRequest request){
//        System.out.println(logRequest.getEventName());
//        System.out.println(System.currentTimeMillis());
        logRequest.setTime(System.currentTimeMillis());
//        logRequest.setIp(request.getRemoteAddr());
//        logRequest.setPlatform(Platform.getPlatform(request.getHeader("User-Agent")));

        logProducer.pushLog(logRequest);
        return logRequest;
    }

    @GetMapping
    String getHeaders(@RequestHeader Map<String, String> headers, HttpServletRequest request){
        log.warn("------------------");
        headers.forEach((key, value) -> {
            log.warn(key + ": " + value);
        });
        log.warn("Client IP: " + request.getRemoteAddr());
        log.warn("User Agent: " + request.getHeader("User-Agent"));
        log.warn("Client Platform: " + Platform.getPlatform(request.getHeader("User-Agent")));

        log.warn("------------------");

        return "hello";
    }

    @GetMapping("/statistics")
    List<Map> getStatistics(){
        return logService.getStatistics();
    }
}
