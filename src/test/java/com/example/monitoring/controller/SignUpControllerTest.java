package com.example.monitoring.controller;


import com.example.monitoring.domain.ResponseMessage;
import com.example.monitoring.domain.User;
import com.example.monitoring.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;


@AutoConfigureMockMvc
@SpringBootTest
public class SignUpControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Test
    @DisplayName("[GET] 회원 가입 페이지 최초 접속")
    public void signupGetTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sign-up"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("sign-up"))
                .andDo(MockMvcResultHandlers.print());;
    }

    @Test
    @DisplayName("[GET] 로그인 성공 후, 회원 가입 페이지 접속")
    public void signupGetTestAfterLogin() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 100);

        mockMvc.perform(MockMvcRequestBuilders.get("/sign-up").session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/main.jsp"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[POST] 회원가입 시도")
    public void signupPostTest() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("email", "test@namver.com");
        params.add("password", "1q2w3e4r!");
        params.add("password_check", "1q2w3e4r!");
        params.add("name", "TESTER");

        Mockito.when(userService.signup(Mockito.any(User.class))).thenReturn(new ResponseMessage());

        mockMvc.perform(MockMvcRequestBuilders.post("/sign-up")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
