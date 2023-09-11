package com.example.monitoring.domain;

public class User {
    private int user_no;
    private String email;
    private String password;
    private String name;
    private Role role;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserNo() {
        return this.user_no;
    }

    public void setUserNo(int user_no) {
        this.user_no = user_no;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
