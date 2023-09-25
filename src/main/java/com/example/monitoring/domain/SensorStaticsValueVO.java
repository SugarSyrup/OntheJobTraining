package com.example.monitoring.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SensorStaticsValueVO {
    private String name;
    private float AVG;
    private float MAX;
    private float MIN;
    private String date;
    private String location;

    public String toString() {
        return "[" +
                this.name + "," +
                this.AVG + "," +
                this.MAX + "," +
                this.MIN + "," +
                this.date + "," +
                this.location + "," +
                "],";
    }
}
