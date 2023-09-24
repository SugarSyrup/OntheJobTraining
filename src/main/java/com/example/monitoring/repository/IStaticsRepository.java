package com.example.monitoring.repository;

import com.example.monitoring.domain.Temperature;
import com.example.monitoring.domain.TemperatureStaticsVO;

import java.util.List;

public interface IStaticsRepository {
    public List<TemperatureStaticsVO> getTemperatureStatics(String location, String name, String startDate, String endDate) throws Exception;

    public List<TemperatureStaticsVO> getHumidityStatics(String location, String name, String startDate, String endDate) throws Exception;

    public List<String> getTemperatureEquipmentsList(String location, String name, String startDate, String endDate) throws Exception;

    public List<String> getHumidityEquipmentsList(String location, String name, String startDate, String endDate) throws Exception;

    public List<Temperature> getDateTemperatures(String date) throws Exception;

    public List<Temperature> getDateHumidities(String date) throws Exception;
}
