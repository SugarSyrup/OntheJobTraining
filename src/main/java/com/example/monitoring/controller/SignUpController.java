package com.example.monitoring.controller;

import com.example.monitoring.domain.ResponseMessage;
import com.example.monitoring.domain.User;
import com.example.monitoring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

@Controller
public class SignUpController extends HttpServlet {
    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String getIndex(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.isNew() || session.getAttribute("id") == null) {
            return "sign-up";
        } else {
            return "main";
        }
    }

    @PostMapping("/sign-up")
    public String postSignup(HttpServletRequest req) throws Exception {
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String password_check = req.getParameter("password_check");

        String REGEXP_EMAIL = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        String REGEXP_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$";

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);

        ResponseMessage msg = new ResponseMessage();
        if (!Pattern.matches(REGEXP_EMAIL, email)) {
            msg.setOk(false);
            msg.setMessage("이메일 형식이 잘못 되었습니다");
            req.setAttribute("focus", "email");
        } else if (!password_check.equals(password)) {
            msg.setOk(false);
            msg.setMessage("비밀번호를 똑같이 입력해야 합니다.");
            req.setAttribute("focus", "password_check");
        } else if (!Pattern.matches(REGEXP_PASSWORD, password)) {
            msg.setOk(false);
            msg.setMessage("비밀번호는 최소 8자리에서 최대 16자리 숫자,영문,특수문자를 포함해야 합니다.");
            req.setAttribute("focus", "password");
        } else {
            msg = userService.signup(user);
        }

        req.setAttribute("UserInfo", user);
        req.setAttribute("password_check", password_check);
        req.setAttribute("Message", msg);

        if (msg.getOk()) {
            return "login";
        } else {
            return "sign-up";
        }
    }
}