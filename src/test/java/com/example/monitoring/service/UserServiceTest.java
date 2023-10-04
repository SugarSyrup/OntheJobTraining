package com.example.monitoring.service;


import com.example.monitoring.domain.ResponseMessage;
import com.example.monitoring.domain.User;
import com.example.monitoring.domain.UserRole;
import com.example.monitoring.repository.IUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@WebMvcTest(controllers = UserService.class)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private IUserRepository userRepository;
    @InjectMocks
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginTest() {
        //Given
        User testUser = User.builder()
                .email("test1@naver.com")
                .password("1q2w3e4r!")
                .name("TESTER 1")
                .userRole(UserRole.USER)
                .user_no(123)
                .build();

        //stub
        BDDMockito.given(userRepository.findByEmail(testUser.getEmail())).willReturn(Optional.of(testUser));

        //When
        ResponseMessage result = userService.login("test1@naver.com", "1q2w3e4r!");

        User repoResult = userRepository.findByEmail("test1@naver.com").orElseGet(() -> new User());
        ResponseMessage message = new ResponseMessage();
        if(passwordEncoder.matches(testUser.getPassword(), repoResult.getPassword())) {
            message.setMessage(Integer.toString(repoResult.getUser_no()));
            message.setOk(true);
        }
        else {
            message.setMessage("Password Not Matched!");
            message.setOk(false);
        }

        //then
        Assertions.assertThat(result.isOk()).isEqualTo(result.isOk());
        Assertions.assertThat(result.getMessage()).isEqualTo(result.getMessage());
    }
}
