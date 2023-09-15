package com.example.monitoring.repository;

import com.example.monitoring.domain.Equipment;

import java.util.List;

public interface IEquipementRepository {
    List<Equipment> findAll();
    List<Equipment> findAllByName(String name);

    Equipment findEquipmentById(String key);
    List<Equipment> findAllByNameNLocation(String location, String name);
    List<Equipment> findAllByNameNDivision(String division, String name);
    List<Equipment> findAllByAllConditions(String location, String division, String name);
    List<String> findLocations();
    boolean findDuplicatedName(String name);

    void saveEquipment(Equipment equipment);

    void updateEquipment(Equipment equipment);

    void deleteEquipmentByName(String name);
}
