package com.example.monitoring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
public class FeaturesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[GET] 데이터 조회 메인 화면 접근")
    public void getMainPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/main"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[GET] 장비 조회 화면 접근")
    public void getEquipmentsPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/equipment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("[GET] 유저 관리 화면 접근")
    public void getUsersPageTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("isAdmin", true);

        mockMvc.perform(MockMvcRequestBuilders.get("/users").session(session))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andDo(MockMvcResultHandlers.print());
    }
}