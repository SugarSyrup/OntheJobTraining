package com.example.monitoring.controller;

import com.example.monitoring.domain.Msg;
import com.example.monitoring.domain.User;
import com.example.monitoring.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoginAPIController {
    private final UserService userService;

            public LoginAPIController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, path="/login")
    public Msg Post(@RequestBody User user) {
        Msg msg = new Msg();
        msg.setMessage(userService.findUserByEmail(user.getEmail(), user.getPassword()));
        return msg;
    }

    @RequestMapping(method = RequestMethod.GET, path="/users")
    public List<User> GetUsers() {
        return userService.findUsers();
    }
}