package com.example.monitoring.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
public class TemperatureStaticsVO {
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
