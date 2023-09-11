package com.example.monitoring.controller;

import com.example.monitoring.domain.ResponseMesssage;
import com.example.monitoring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;

@Controller
public class LoginController{
    private final UserService userService;

    public LoginController(UserService userService) { this.userService = userService; }
    @GetMapping("/")
    public String getIndex(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if(session.isNew() || session.getAttribute("id") == null) {
            return "login";
        } else {
            return "redirect:/main";
        }
    }


    @PostMapping("/")
    public String postIndex(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        ResponseMesssage msg = userService.findUserByEmail(email, password);
        req.setAttribute("Message", msg);

        if(msg.getOk()) {
            HttpSession session = req.getSession();
            System.out.println(msg.getMessage());
            session.setAttribute("id", msg.getMessage());

            return "redirect:/main";
        } else {
            return "login";
        }
    }
}
