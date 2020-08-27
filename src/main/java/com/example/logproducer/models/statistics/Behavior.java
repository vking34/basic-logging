package com.example.logproducer.models.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Behavior {

    @Column(name = "event_name")
    @JsonProperty("event_name")
    private String eventName;

    private Long count;

    private Date date;

    public Behavior(String eventName, Long count, Date date) {
        this.eventName = eventName;
        this.count = count;
        this.date = date;
    }
}
