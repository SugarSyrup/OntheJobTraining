package com.example.monitoring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {
    @GetMapping("/main")
    public String getMain(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if(session.isNew() || session.getAttribute("id") == null) {
            return "redirect:/";
        } else {
            return "main";
        }
    }
}
