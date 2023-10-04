package com.example.monitoring.dto;

import com.example.monitoring.domain.UserRole;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long no;
    private String email;
    private String password;
    private String name;
    private UserRole userRole;
    private String regist_date;

}
