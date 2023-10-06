package com.example.monitoring.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private int user_no;
    private String email;
    private String password;
    private String name;
    private UserRole role;
    private LocalDate regist_date;
    private LocalDate up_date;
}
