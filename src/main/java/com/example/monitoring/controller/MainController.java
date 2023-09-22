package com.example.monitoring.controller;

import com.example.monitoring.domain.Temperature;
import com.example.monitoring.domain.TemperatureStaticsVO;
import com.example.monitoring.repository.EquipementRepository;
import com.example.monitoring.service.EquipmentService;
import com.example.monitoring.service.StaticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

        System.out.println(this.startDate);
        System.out.println(this.endDate);

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
    public List<Temperature> postInfo(HttpServletRequest req, @RequestBody Temperature temperatureVO)  throws Exception {
        List<Temperature> _tmp = staticsService.getDateInfo(this.division,temperatureVO.getDate());
        System.out.println("post worked");
        System.out.println(_tmp);
        return _tmp;
    };
}

