package com.example.logproducer;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Statistic {
    private String date;
    private Integer shopId;
    private Integer userId;
    private String event;
    private Integer impression;
    private Integer click;
    private String platform;
}
