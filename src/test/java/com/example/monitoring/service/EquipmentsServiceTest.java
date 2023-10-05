package com.example.monitoring.service;

import com.example.monitoring.domain.Equipment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Equipment Service 테스트")
@SpringBootTest
public class EquipmentsServiceTest {
    @Autowired
    private EquipmentService equipmentService;

    Equipment testEquipment = null;

    @BeforeEach
    void beforeEach() {
        testEquipment = Equipment.builder()
                .name("TEST 장비")
                .location("TEST 지역")
                .division("습도")
                .build();
    }

    @Order(1)
    @Test
    @DisplayName("장비 생성")
    public void createEquipment() {
        List<Equipment> beforeResults = equipmentService.findAll();
        equipmentService.saveEquipment(testEquipment);
        List<Equipment> afterResults = equipmentService.findAll();

        Assertions.assertThat(beforeResults.size() + 1).isEqualTo(afterResults.size());
    }

    @Order(2)
    @Test
    @DisplayName("장비 이름 중복 검사")
    public void duplicateCheckEquipment() {

    }

    @Order(3)
    @Test
    @DisplayName("장비 조회")
    public void readEquipment() {
        List<Equipment> result = equipmentService.findEquipments(testEquipment.getDivision(), testEquipment.getLocation(), testEquipment.getName());
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getName()).isEqualTo("TEST 장비");
    }

    @Order(4)
    @Test
    @DisplayName("장비 수정")
    public void updateEquipment() {
        Equipment prev = equipmentService.findEquipments(testEquipment.getDivision(), testEquipment.getLocation(), testEquipment.getName()).get(0);
        prev.setId(prev.getName());
        prev.setLocation("CHANGED TEST 지역");
        System.out.println(prev);
        equipmentService.updateEquipment(prev);

        Equipment result = equipmentService.findEquipments(testEquipment.getDivision(), testEquipment.getLocation(), testEquipment.getName()).get(0);

        Assertions.assertThat(result.getLocation()).isEqualTo("CHANGED TEST 지역");
    }

    @Order(5)
    @Test
    @DisplayName("장비 삭제")
    public void deleteEquipment() {
        equipmentService.deleteEquipmentByName(testEquipment.getName());
        List<Equipment> result = equipmentService.findEquipments(testEquipment.getDivision(), testEquipment.getLocation(), testEquipment.getName());

        Assertions.assertThat(result.size()).isZero();
    }
}
