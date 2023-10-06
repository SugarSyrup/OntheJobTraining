package com.example.monitoring.controller;

import com.example.monitoring.domain.Equipment;
import com.example.monitoring.domain.SensorInfoPostBody;
import com.example.monitoring.domain.SensorStaticsValueVO;
import com.example.monitoring.domain.SensorValueVO;
import com.example.monitoring.service.EquipmentService;
import com.example.monitoring.service.StaticsService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class MainController {

    EquipmentService equipmentService;
    StaticsService staticsService;

    String division;
    List<String> temperatureLocations;
    List<String> humidityLocations;
    String location;
    String startDate;
    String endDate;
    String name;

    public MainController(StaticsService staticsService, EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
        this.staticsService = staticsService;
        this.division = "기온";
        this.temperatureLocations = equipmentService.findLocations("기온");
        this.humidityLocations = equipmentService.findLocations("습도");
        this.location = "";
        this.startDate = "";
        this.endDate = "";
        this.name = "";
    }

    @GetMapping("/main")
    public String getMain(HttpServletRequest req) throws Exception {
        HttpSession session = req.getSession();

        ServletContext servCon = req.getServletContext();
        servCon.setAttribute("modalID", "");

        req.setAttribute("division", this.division);
        req.setAttribute("location", this.location);
        req.setAttribute("name", this.name);
        req.setAttribute("startDate", this.startDate);
        req.setAttribute("endDate", this.endDate);
        req.setAttribute("humidity_locations", this.humidityLocations);
        req.setAttribute("temperature_locations", this.temperatureLocations);
        req.setAttribute("temperature_statics", staticsService.getTemperatureStatics(this.division, this.location, this.name, this.startDate, this.endDate));
        req.setAttribute("equipment_list", staticsService.getEquipmentsList(this.division, this.location, this.name, this.startDate, this.endDate));
        if(session.isNew() || session.getAttribute("id") == null) {
            return "redirect:/";
        } else {
            req.setAttribute("where", "main");
            return "main";
        }
    }

    @PostMapping("/main/search")
    public String postSearch(HttpServletRequest req) {
        this.division = req.getParameter("division");
        this.location = req.getParameter("location");
        this.startDate = req.getParameter("start_date");
        this.endDate = req.getParameter("end_date");
        this.name = req.getParameter("equipment_name");

        return "redirect:/";
    }

    @PostMapping("/main/reset")
    public String postReset(HttpServletRequest req) {
        this.division = "기온";
        this.temperatureLocations = equipmentService.findLocations("기온");
        this.humidityLocations = equipmentService.findLocations("습도");
        this.location = "";
        this.startDate = "";
        this.endDate = "";
        this.name = "";

        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/main/info")
    public List<SensorValueVO> postInfo(HttpServletRequest req, @RequestBody SensorInfoPostBody sensorInfoPostBody)  throws Exception {
        List<SensorValueVO> dateInfo = staticsService.getDateInfo(this.division, sensorInfoPostBody.getDate(), sensorInfoPostBody.getName());
        return dateInfo;
    }

    @GetMapping("/api/monitoring/download")
    public void valuesDownload(HttpServletRequest req, HttpServletResponse res) throws Exception{
        Workbook workbook = new XSSFWorkbook();
        Sheet staticSheet = workbook.createSheet("Statics");

        int rowCount = 0;
        String headerNames[] = new String[]{"장비명","일시", "평균값", "최대값", "최소값"};

        Row headerRow = null;
        Cell headerCell = null;

        headerRow = staticSheet.createRow(rowCount++);
        for(int i = 0; i < headerNames.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(headerNames[i]);
        }

        Row bodyRow = null;
        Cell bodyCell = null;

        List<SensorStaticsValueVO> statics = staticsService.getTemperatureStatics(this.division, this.location, this.name, this.startDate, this.endDate);
        for (SensorStaticsValueVO _static : statics) {
            bodyRow = staticSheet.createRow(rowCount++);
            bodyCell = bodyRow.createCell(0);
            bodyCell.setCellValue(_static.getName());

            bodyCell = bodyRow.createCell(1);
            bodyCell.setCellValue(_static.getDate().substring(0,10));

            bodyCell = bodyRow.createCell(2);
            bodyCell.setCellValue(_static.getAVG());

            bodyCell = bodyRow.createCell(3);
            bodyCell.setCellValue(_static.getMAX());

            bodyCell = bodyRow.createCell(4);
            bodyCell.setCellValue(_static.getMIN());
        }

        List<String> equipments = staticsService.getEquipmentsList(this.division, this.location, this.name, this.startDate, this.endDate);
        for(var j = 0; j < equipments.size(); j++) {
            Sheet sheet = workbook.createSheet(equipments.get(j));

            rowCount = 0;
            headerNames = new String[]{"일시", "측정값"};

            headerRow = sheet.createRow(rowCount++);
            for(int i = 0; i < headerNames.length; i++) {
                headerCell = headerRow.createCell(i);
                headerCell.setCellValue(headerNames[i]);
            }

            List<SensorValueVO> values = staticsService.getValues(this.division, this.location, this.name, this.startDate, this.endDate, equipments.get(j));
            for (SensorValueVO value : values) {
                bodyRow = sheet.createRow(rowCount++);
                bodyCell = bodyRow.createCell(0);
                bodyCell.setCellValue(value.getDate());

                bodyCell = bodyRow.createCell(1);
                bodyCell.setCellValue(value.getValue());
            }
        }

        String filename = "tmp";


        res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        res.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
        ServletOutputStream servletOutputStream = res.getOutputStream();

        workbook.write(servletOutputStream);
        workbook.close();

        servletOutputStream.flush();
        servletOutputStream.close();
    }
}