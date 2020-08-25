package com.example.logproducer.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class StatisticKey implements Serializable {

//    @Column(insertable = false, updatable = false)
    private String event_name;

    @Column(name ="user_id")
    private Integer userId;

    @Column(name ="shop_id", insertable = false, updatable = false)
    private Integer shopId;

//    @Column(insertable = false, updatable = false)
    private String date;
}
