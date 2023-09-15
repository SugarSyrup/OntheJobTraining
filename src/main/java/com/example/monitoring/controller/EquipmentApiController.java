package com.example.monitoring.controller;

import com.example.monitoring.domain.Equipment;
import com.example.monitoring.service.EquipmentService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EquipmentApiController {
    EquipmentService equipmentService;

    public EquipmentApiController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/api/equipment/update/info")
    @ResponseBody
    public Map<String, String> UpdateUserInfo(HttpServletRequest req) {
        String id = req.getParameter("id");
        Map<String, String> resultMap = new HashMap<String,String>();
        Equipment result = equipmentService.findEquipmentById(id);
        resultMap.put("name", result.getName());
        resultMap.put("division", result.getDivision());
        resultMap.put("location", result.getLocation());

        return resultMap;
    }

    @PostMapping("/api/equipment/duplicated-check")
    @ResponseBody
    public Map<String, Boolean> DuplicatedCheck(HttpServletRequest req, @RequestBody Equipment equipment) {
        String name = equipment.getName();
        System.out.println("name : " + equipment);
        Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
        boolean flag = equipmentService.findDuplicatedName(name);
        resultMap.put("flag", flag);

        return resultMap;
    }
}
