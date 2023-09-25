package com.example.monitoring.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseMessage {
    private String message;
    private boolean ok;

    public ResponseMessage() {
        this.ok = false;
        this.message = "";
    }
}
