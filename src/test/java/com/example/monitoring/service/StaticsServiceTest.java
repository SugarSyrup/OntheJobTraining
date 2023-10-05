package com.example.monitoring.service;

import com.example.monitoring.domain.SensorStaticsValueVO;
import com.example.monitoring.domain.SensorValueVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("통계 데이터 테스트")
@SpringBootTest
public class StaticsServiceTest {
    @Autowired
    private StaticsService staticsService;

    @Order(1)
    @Test
    @DisplayName("[GET] 기온 통계 데이터 수신 테스트")
    public void getTemperatureStaticsValueTest() throws Exception{
        List<SensorStaticsValueVO> result = staticsService.getTemperatureStatics("기온","","","","");
        Assertions.assertThat(result.size()).isNotZero();
    }

    @Order(2)
    @Test
    @DisplayName("[GET] 습도 통계 데이터 수신 테스트")
    public void getHumidityStaticsValueTest() throws Exception{
        List<SensorStaticsValueVO> result = staticsService.getTemperatureStatics("습도","","","","");
        Assertions.assertThat(result.size()).isNotZero();
    }

    @Order(3)
    @Test
    @DisplayName("[GET] 기온 장비 데이터 수신 테스트")
    public void getTemperatureEquipmentsTest() throws Exception {
        List<String> result = staticsService.getEquipmentsList("기온","","", "", "");
        Assertions.assertThat(result.size()).isNotZero();
    }

    @Order(4)
    @Test
    @DisplayName("[GET] 습도 장비 데이터 수신 테스트")
    public void getHumidityEquipmentsTest() throws Exception {
        List<String> result = staticsService.getEquipmentsList("습도","","", "", "");
        Assertions.assertThat(result.size()).isNotZero();
    }

    @Order(5)
    @Test
    @DisplayName("[GET] 23-08-01 기온 데이터 수신 테스트")
    public void getTemperatureOneDayToday() throws Exception{
        List<SensorValueVO> result = staticsService.getDateInfo("기온","2023-08-01","1번 장비");
        Assertions.assertThat(result.size()).isNotZero();
    }
    @Order(6)
    @Test
    @DisplayName("[GET] 23-08-01 습도 데이터 수신 테스트")
    public void getHumidityOneDayToday() throws Exception{
        List<SensorValueVO> result = staticsService.getDateInfo("습도","2023-08-01","3번 장비");
        Assertions.assertThat(result.size()).isNotZero();
    }
}
