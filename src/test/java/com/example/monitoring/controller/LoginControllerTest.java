package com.example.monitoring.controller;

import com.example.monitoring.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@AutoConfigureMockMvc
@SpringBootTest
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[GET] 로그인 페이지 최초 접속")
    public void getLoginTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/login.jsp"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[GET] 로그인 성공 후, 로그인 페이지 접속")
    public void getLoginTestAfterLogin() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 100);

        mockMvc.perform(MockMvcRequestBuilders.get("/").session(session))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/main"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[POST] 로그인 시도")
    public void postLogin() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("email", "admin@naver.com");
        params.add("password", "1q2w3e4r!");

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.post("/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .params(params));

        actions.andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/main"));
    }
}