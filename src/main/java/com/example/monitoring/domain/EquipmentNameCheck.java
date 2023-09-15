package com.example.monitoring.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EquipmentNameCheck {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private boolean temperature;

    @JsonProperty
    private boolean humidity;

    @JsonProperty
    private String location;

    @JsonProperty
    private String URI;

    @JsonProperty
    private String modal_id;



    public EquipmentNameCheck() {
        this.name = "";
        this.temperature = false;
        this.humidity = false;
        this.location = "";
        this.URI = "";
        this.modal_id = "";
    }

    @Override
    public String toString() {
        return "DuplicatedNotBody{" +
                "email='" + name + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", location='" + location + '\'' +
                ", URI='" + URI + '\'' +
                ", modal_id='" + modal_id + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String email) {
        this.name = email;
    }

    public boolean getTemperature() {
        return temperature;
    }

    public void setTemperature(boolean temperature) {
        this.temperature = temperature;
    }

    public boolean getHumidity() {
        return humidity;
    }

    public void setHumidity(boolean humidity) {
        this.humidity = humidity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getModal_id() {
        return modal_id;
    }

    public void setModal_id(String modal_id) {
        this.modal_id = modal_id;
    }

}
