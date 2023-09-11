package com.example.monitoring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserEditController {
    @GetMapping("/user-edit")
    public String GetUserEdit(HttpServletRequest req) {
        HttpSession session = req.getSession();
        System.out.println(session.getAttribute("id"));
        return "user-edit";
    }
}
