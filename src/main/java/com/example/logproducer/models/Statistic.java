package com.example.logproducer.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EmbeddedId;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "periodic_summary")
public class Statistic {

    @EmbeddedId
    private StatisticKey statisticKey;

    @Column(insertable = false, updatable = false)
    private String event_name;

    @Column(name ="user_id", insertable = false, updatable = false)
    private Integer userId;

    @Column(name ="shop_id", insertable = false, updatable = false)
    private Integer shopId;

    private String platform;
    private Integer impression;
    private Integer click;

    @Column(insertable = false, updatable = false)
    private String date;
}
