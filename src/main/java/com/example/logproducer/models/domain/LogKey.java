package com.example.logproducer.models.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class LogKey implements Serializable {
    private String event_name;

    @Column(name ="user_id")
    private Integer userId;

    private Date time;
}
