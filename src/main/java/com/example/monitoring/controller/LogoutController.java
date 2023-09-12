package com.example.monitoring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {
    @PostMapping("/api/logout")
    public String PostLogout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("id");
        return "redirect:/";
    }
}
