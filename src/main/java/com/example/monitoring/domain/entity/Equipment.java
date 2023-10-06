package com.example.monitoring.domain.entity;

import com.example.monitoring.domain.dto.EquipmentDto;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Equipment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_no")
    private Long equipment_no;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "division")
    private String division;

    @Column(name = "regist_date")
    private Timestamp regist_date;

    @Column(name = "up_date")
    private Timestamp up_date;

    @Column(name = "state")
    private int state;

}
