package com.example.monitoring.controller;

import com.example.monitoring.domain.User;
import com.example.monitoring.domain.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpSession;

@AutoConfigureMockMvc
@SpringBootTest
public class UserEditControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[GET] 유저 정보 수정 페이지 접속, 로그인 X")
    public void getUserEditTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user-edit"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[GET] 유저 정보 수정 페이지 접속, 로그인 O")
    public void getUserEditTestAfterLogin() throws Exception{
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 100);

        mockMvc.perform(MockMvcRequestBuilders.get("/user-edit"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[POST] 유저 정보 수정")
    public void postUserEditTest() throws Exception{
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", "64");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("name", "NOT TESTER");


        mockMvc.perform(MockMvcRequestBuilders.post("/user-edit").session(session)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/main"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[DELETE] 유저 탈퇴")
    public void deleteUser() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", "64");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user-edit").session(session))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"))
                .andDo(MockMvcResultHandlers.print());
    }

}
