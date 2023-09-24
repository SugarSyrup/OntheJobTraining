package com.example.monitoring.service;

import com.example.monitoring.domain.Temperature;
import com.example.monitoring.domain.TemperatureStaticsVO;
import com.example.monitoring.repository.IStaticsRepository;

import java.util.List;

public class StaticsService {
    private final IStaticsRepository staticsRepository;

    public StaticsService(IStaticsRepository staticsRepository) {
        this.staticsRepository = staticsRepository;
    }

    public List<TemperatureStaticsVO> getTemperatureStatics(String division, String location, String name, String startDate, String endDate) throws Exception {
        if(division.equals("기온")) {
            return staticsRepository.getTemperatureStatics(location, name, startDate, endDate);
        } else {
            return staticsRepository.getHumidityStatics(location, name, startDate, endDate);
        }
    }

    public List<String> getEquipmentsList(String division, String location, String name, String startDate, String endDate) throws Exception {
        if(division.equals("기온")) {
            return staticsRepository.getTemperatureEquipmentsList(location, name, startDate, endDate);
        } else {
            return staticsRepository.getHumidityEquipmentsList(location, name, startDate, endDate);
        }
    }

    public List<Temperature> getDateInfo(String division, String date)  throws Exception {
        if(division.equals("기온")) {
            return staticsRepository.getDateTemperatures(date);
        } else {
            return staticsRepository.getDateHumidities(date);
        }
    }
}
