package com.example.monitoring.service;

import com.example.monitoring.domain.Equipment;
import com.example.monitoring.repository.IEquipementRepository;

import java.util.ArrayList;
import java.util.List;

public class EquipmentService {
    private final IEquipementRepository equipmentRepository;

    public EquipmentService(IEquipementRepository equipementRepository) {
        this.equipmentRepository = equipementRepository;
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public List<String> findLocations() {
        return equipmentRepository.findLocations();
    }

    public List<String> findLocations(String division) {
            return equipmentRepository.findLocations(division);
    }

    public List<Equipment> findEquipments(String division, String location, String name) {
        List<Equipment> equipments = new ArrayList<Equipment>();
        Equipment equipment = new Equipment();
        equipment.setName(name);
        equipment.setLocation(location);
        equipment.setDivision(division);

        equipments = equipmentRepository.findAllByConditions(equipment);
        return equipments;
    }

    public Equipment findEquipmentById(String key) {
        return equipmentRepository.findEquipmentById(key);
    }

    public boolean findDuplicatedName(String name) {
        return equipmentRepository.findDuplicatedName(name);
    }

    public void saveEquipment(Equipment equipment) {
        equipmentRepository.saveEquipment(equipment);
    }

    public void updateEquipment(Equipment equipment) {
        equipmentRepository.updateEquipment(equipment);
    }

    public void deleteEquipmentByName(String name) { equipmentRepository.deleteEquipmentByName(name); }
}
