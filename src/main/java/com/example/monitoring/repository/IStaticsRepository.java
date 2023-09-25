package com.example.monitoring.repository;

import com.example.monitoring.domain.SensorValueVO;
import com.example.monitoring.domain.SensorStaticsValueVO;

import java.util.List;

public interface IStaticsRepository {
    public List<SensorStaticsValueVO> getTemperatureStatics(String location, String name, String startDate, String endDate) throws Exception;

    public List<SensorStaticsValueVO> getHumidityStatics(String location, String name, String startDate, String endDate) throws Exception;

    public List<String> getTemperatureEquipmentsList(String location, String name, String startDate, String endDate) throws Exception;

    public List<String> getHumidityEquipmentsList(String location, String name, String startDate, String endDate) throws Exception;

    public List<SensorValueVO> getDateTemperatures(String date, String name) throws Exception;

    public List<SensorValueVO> getDateHumidities(String date, String name) throws Exception;
}
