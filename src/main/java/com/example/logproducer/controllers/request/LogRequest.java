package com.example.logproducer.controllers.request;

import com.example.logproducer.models.domain.Platform;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class LogRequest {

    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("shop_id")
    private Integer shopId;

    private Platform platform;
    private String ip;
    private String city;
    private String country;

    private Date time;

//    private Long impression;
//    private Long click;

//    @Override
//    public String toString(){
//
//    }
}
