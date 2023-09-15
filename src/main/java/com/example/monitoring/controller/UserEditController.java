package com.example.monitoring.controller;

import com.example.monitoring.domain.User;
import com.example.monitoring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserEditController {
    private final UserService userService;

    public UserEditController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/user-edit")
    public String GetUserEdit(HttpServletRequest req) {
        HttpSession session = req.getSession();

        ServletContext servCon = req.getServletContext();
        servCon.setAttribute("modalID", "");

        User user = userService.findUserByKey((String) session.getAttribute("id"));

        if(user == null) {
            session.removeAttribute("id");
            return "redirect:/";
        }
        else {
            req.setAttribute("name",user.getName());
            req.setAttribute("email",user.getEmail());

            return "user-edit";
        }
    }

    @PostMapping("/user-edit")
    public String PostUserEdit(HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = userService.findUserByKey((String) session.getAttribute("id"));

        user.setName((String) req.getParameter("name"));
        if(req.getAttribute("password") != null){
            user.setPassword((String) req.getParameter("password"));
        }

        boolean ok = userService.updateUser(user);
        if( !ok ) {
            return "redirect:/user-edit";
        } else {
            return "redirect:/main";
        }
    }

    @DeleteMapping("/api/user-edit")
    public String DeleteUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String key = (String) session.getAttribute("id");

        userService.deleteUserByKey(key);
        session.removeAttribute("id");
        return "redirect:/";
    }
}
