package com.example.monitoring.domain;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private int user_no;
    private String email;
    private String password;
    private String name;
    private UserRole userRole;

    private String regist_date;
}
