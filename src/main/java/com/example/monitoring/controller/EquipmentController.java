package com.example.monitoring.controller;

import com.example.monitoring.domain.EquipmentNameCheck;
import com.example.monitoring.domain.Equipment;
import com.example.monitoring.domain.ResponseMessage;
import com.example.monitoring.service.EquipmentService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class EquipmentController {
    EquipmentService equipmentService;
    List<Equipment> equipments;
    String division;
    String location;
    String name;
    String divisionErrorMsg;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
        this.equipments = equipmentService.findEquipments("","","");
        this.division = "";
        this.location = "";
        this.name = "";
        this.divisionErrorMsg = "";
    }

    @GetMapping("/equipment")
    public String GetEquipment(HttpServletRequest req) {
        this.equipments = equipmentService.findEquipments(division, location, name);
        req.setAttribute("equipment_locations", equipmentService.findLocations());
        req.setAttribute("division", this.division);
        req.setAttribute("equipments", this.equipments);
        req.setAttribute("location", this.location);
        req.setAttribute("name", this.name);
        req.setAttribute("divisionErrorMsg", this.divisionErrorMsg);

        return "equipment";
    }

    @PostMapping("/equipment")
    public String PostEquipment(HttpServletRequest req) {
        this.division = req.getParameter("division");
        this.location = req.getParameter("location");
        this.name = req.getParameter("name");

        this.equipments = equipmentService.findEquipments(division, location, name);

        return "redirect:/equipment";
    }

    @PostMapping("/api/equipment/reset")
    public String PostReset(HttpServletRequest req) {
        this.equipments = equipmentService.findAll();
        this.division = "";
        this.location = "";
        this.name = "";

        req.getServletContext().setAttribute("response_msg", null);
        req.getServletContext().setAttribute("modalID", "");
        req.getServletContext().setAttribute("formConditions", null);

        return "redirect:/equipment";
    }

    @PostMapping("/equipment/duplicated-check")
    public String DuplicateName(HttpServletRequest req, @RequestBody EquipmentNameCheck duplicatedNotBody) {
        boolean flag = equipmentService.findDuplicatedName(duplicatedNotBody.getName());
        ServletContext servCon = req.getServletContext();

        ResponseMessage msg = new ResponseMessage();
        if(flag) {
            msg.setOk(false);
            msg.setMessage("중복된 장비명 입니다.");
        } else {
            msg.setOk(true);
            msg.setMessage(duplicatedNotBody.getName());
        }

        servCon.setAttribute("response_msg", msg);
        servCon.setAttribute("modalID", duplicatedNotBody.getModal_id());
        servCon.setAttribute("formConditions", duplicatedNotBody);
        return "redirect:/" + duplicatedNotBody.getURI();
    }

    @PostMapping("/api/equipment/register")
    public String RegistEquipment(HttpServletRequest req, @RequestBody EquipmentNameCheck duplicatedNotBody) {
        Equipment equipment = new Equipment();
        equipment.setName(duplicatedNotBody.getName());
        equipment.setLocation(duplicatedNotBody.getLocation());

        if (!duplicatedNotBody.getTemperature()) {
            this.divisionErrorMsg="";
            equipment.setDivision("{습도}");
            equipmentService.saveEquipment(equipment);
        } else if (!duplicatedNotBody.getHumidity()){
            this.divisionErrorMsg="";
            equipment.setDivision("{기온}");
            equipmentService.saveEquipment(equipment);
        } else {
            this.divisionErrorMsg="";
            equipment.setDivision("{기온, 습도}");
            equipmentService.saveEquipment(equipment);
        }

        ServletContext servCon = req.getServletContext();
        servCon.setAttribute("response_msg", null);
        servCon.setAttribute("modalID", "");
        servCon.setAttribute("formConditions", null);

        return "redirect:/equipment";
    }

    @PostMapping("/api/equipment/update")
    public String UpdateEquipment(HttpServletRequest req, @RequestBody EquipmentNameCheck duplicatedNotBody) {
        Equipment equipment = new Equipment();
        equipment.setId(duplicatedNotBody.getId());
        equipment.setName(duplicatedNotBody.getName());
        equipment.setLocation(duplicatedNotBody.getLocation());

        if (!duplicatedNotBody.getTemperature()) {
            equipment.setDivision("{습도}");
            equipmentService.updateEquipment(equipment);
        } else if (!duplicatedNotBody.getHumidity()){
            equipment.setDivision("{기온}");
            equipmentService.updateEquipment(equipment);
        } else {
            equipment.setDivision("{기온, 습도}");
            equipmentService.updateEquipment(equipment);
        }

        return "redirect:/equipment";
    }

    @PostMapping("/api/equipment-delete")
    public String DeleteUser(HttpServletRequest req, @RequestBody Equipment equipment) {

        equipmentService.deleteEquipmentByName(equipment.getName());

        return "redirect:/equipment";
    }

    @GetMapping("/api/equipment/download")
    public void downloadEquipments(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1"); //엑셀 Sheet 이름

        int rowCount = 0;
        String headerNames[] = new String[]{"장비명", "지역", "구분", "등록 날짜"};

        Row headerRow = null;
        Cell headerCell = null;

        headerRow = sheet.createRow(rowCount++);
        for(int i = 0; i < headerNames.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headerNames[i]);
        }

        Row bodyRow = null;
        Cell bodyCell = null;

        for(Equipment equipment : equipments) {
            bodyRow = sheet.createRow(rowCount++);
            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(equipment.getName());

            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue(equipment.getLocation());

            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(equipment.getDivision().substring(1, equipment.getDivision().length()-1));

            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellValue(equipment.getRegist_date());
        }

        String fileName = "equipments";

        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream servletOutputStream = res.getOutputStream();

        workbook.write(servletOutputStream);
        workbook.close();

        servletOutputStream.flush();
        servletOutputStream.close();
    }
}
