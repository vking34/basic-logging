package com.example.logproducer.models.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "logs")
public class Log {

    @EmbeddedId
    @JsonIgnore
    private LogKey key;

    @Column(insertable = false, updatable = false)
    private String event_name;

    @Column(name ="user_id", insertable = false, updatable = false)
    private Integer userId;

    @Column(name ="shop_id")
    private Integer shop_id;

    private String platform;
    private String city;
    private String country;

    @Column(insertable = false, updatable = false)
    private Date time;

}
