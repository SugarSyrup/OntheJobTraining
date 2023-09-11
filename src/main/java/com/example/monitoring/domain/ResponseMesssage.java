package com.example.monitoring.domain;

public class ResponseMesssage {
    private String message;
    private boolean ok;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getOk() { return this.ok; }

    public void setOk(boolean ok) { this.ok = ok;}
}
