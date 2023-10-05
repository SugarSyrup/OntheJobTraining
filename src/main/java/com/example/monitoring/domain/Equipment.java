package com.example.monitoring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    private String id;
    private String name;
    private String location;
    private String division;
    private String regist_date;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getRegist_date() {
        return regist_date;
    }

    public void setRegist_date(String regist_date) {
        this.regist_date = regist_date;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", division='" + division + '\'' +
                ", regist_date='" + regist_date + '\'' +
                '}';
    }
}
