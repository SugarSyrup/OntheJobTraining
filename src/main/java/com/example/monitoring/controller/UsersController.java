package com.example.monitoring.controller;

import com.example.monitoring.domain.Role;
import com.example.monitoring.domain.User;
import com.example.monitoring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersController {
    private UserService userService;
    private List<User> users;
    private String name;
    private String role;

    public UsersController(UserService userService) {
        System.out.println("Contorller starts");
        this.userService = userService;
        this.name = "";
        this.role = "NONE";
        this.users = new ArrayList<User>(userService.getUsers());
    }

    @GetMapping("/users")
    public String GetUsers(HttpServletRequest req) {
        HttpSession session = req.getSession();

        if(role.equals("NONE")) {
            this.users = userService.findUsersByName(name);
        }
        else {
            Role userRole = Role.valueOf(role);
            this.users = userService.findUsersByNameNRole(name, userRole);
        }

        req.setAttribute("name", this.name);
        req.setAttribute("role", this.role);
        req.setAttribute("userList", users);

        if(session.isNew() || session.getAttribute("id") == null) {
            return "redirect:/";
        } else {
            return "users";
        }
    }

    @PostMapping("/users")
    public String PostUsers(HttpServletRequest req) {
        this.name = req.getParameter("name");
        this.role = req.getParameter("role");

        if(role.equals("NONE")) {
            this.users = userService.findUsersByName(name);
        }
        else {
            Role userRole = Role.valueOf(role);
            this.users = userService.findUsersByNameNRole(name, userRole);
        }

        req.setAttribute("name", this.name);
        req.setAttribute("role", this.role);
        req.setAttribute("userList", users);

        return "users";
    }

    @PostMapping("/api/user-delete")
    public String DeleteUser(HttpServletRequest req, @RequestBody String user) {
        userService.deleteUserByKey(user.split("\"")[3]);

        return "redirect:/users";
    }

    @PostMapping("/api/role-change")
    public String UserRoleChange(HttpServletRequest req) {
        String role = req.getParameter("role");
        String user_no = req.getParameter("user_no");
        Role userRole = Role.valueOf(role);

        userService.updateUserRole(user_no, userRole);

        return "redirect:/users";
    }
}
