package com.example.monitoring.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.example.monitoring.domain.ResponseMesssage;
import com.example.monitoring.domain.User;
import com.example.monitoring.service.UserService;
import org.apache.coyote.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SignUpController extends HttpServlet {
    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String getIndex(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if(session.isNew() || session.getAttribute("no") == null) {
            return "sign-up";
        } else {
            return "main";
        }
    }

    @PostMapping("/sign-up")
    public String postSignup(HttpServletRequest req) {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password_check = req.getParameter("password_check");
        String name = req.getParameter("name");

        ResponseMesssage msg = new ResponseMesssage();
        if(!password_check.equals(password)) {
            msg.setOk(false);
            msg.setMessage("비밀번호를 똑랕이 입력해야 합니다.");
        } else {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(password);

            msg = userService.save(user);
        }

        req.setAttribute("Message", msg);
        if(msg.getOk()) {
            return "login";
        } else {
            return "sign-up";
        }

    }
}