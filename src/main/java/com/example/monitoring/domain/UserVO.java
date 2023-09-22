package com.example.monitoring.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.text.SimpleDateFormat;

@Getter
@Builder
@ToString
public class UserVO {
    private Long user_no;
    private String email;
    private String password;
    private String name;
    private Role role;
    private SimpleDateFormat regist_date;
    private SimpleDateFormat up_date;
}
