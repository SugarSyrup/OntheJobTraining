package com.example.monitoring.repository;

import com.example.monitoring.domain.Equipment;

import java.util.List;

public interface IEquipementRepository {
    List<Equipment> findAll();
    List<Equipment> findAllByConditions(Equipment equipment);

    Equipment findEquipmentById(String key);
    List<String> findLocations();
    List<String> findLocations(String division);
    boolean findDuplicatedName(String name);

    void saveEquipment(Equipment equipment);

    void updateEquipment(Equipment equipment);

    void deleteEquipmentByName(String name);
}
