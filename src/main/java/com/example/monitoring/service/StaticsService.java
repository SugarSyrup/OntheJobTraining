package com.example.monitoring.service;

import com.example.monitoring.domain.SensorValueVO;
import com.example.monitoring.domain.SensorStaticsValueVO;
import com.example.monitoring.repository.IStaticsRepository;

import java.util.List;

public class StaticsService {
    private final IStaticsRepository staticsRepository;

    public StaticsService(IStaticsRepository staticsRepository) {
        this.staticsRepository = staticsRepository;
    }

    public List<SensorStaticsValueVO> getTemperatureStatics(String division, String location, String name, String startDate, String endDate) throws Exception {
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

    public List<SensorValueVO> getDateInfo(String division, String date, String name)  throws Exception {
        if(division.equals("기온")) {
            return staticsRepository.getDateTemperatures(date, name);
        } else {
            return staticsRepository.getDateHumidities(date, name);
        }
    }


    public List<SensorValueVO> getValues(String division, String location, String name, String startDate, String endDate) throws Exception {
        if(division.equals("기온")) {
            return staticsRepository.getTemperatures(location, name, startDate, endDate);
        } else {
            return staticsRepository.getHumidities(location, name, startDate, endDate);
        }
    }
}
