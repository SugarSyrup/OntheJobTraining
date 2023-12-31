package com.example.monitoring.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SensorValueVO {
    private int temperature_no;
    private int equipment_no;
    private float value;
    private String date;
}
