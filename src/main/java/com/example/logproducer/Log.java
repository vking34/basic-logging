package com.example.logproducer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Log {
    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("shop_id")
    private Long shopId;

    private Platform platform;

    private String ip;

    private String city;

    private String country;

    private Long impression;

    private Long click;

    private Long time;
}
