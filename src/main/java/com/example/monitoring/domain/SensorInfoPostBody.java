package com.example.monitoring.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SensorInfoPostBody {
    private String name;
    private String date;
}
