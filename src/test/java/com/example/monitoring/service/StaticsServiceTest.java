package com.example.monitoring.service;

import com.example.monitoring.repository.IStaticsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class StaticsServiceTest {
    @InjectMocks
    private StaticsService staticsService;

    @Mock
    private IStaticsRepository staticsRepository;

    @InjectMocks
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("[GET] 통계 데이터 수신 테스트")
    public void getStaticsValueTest() {

    }
}
