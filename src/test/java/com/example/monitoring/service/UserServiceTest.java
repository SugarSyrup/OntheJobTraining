package com.example.monitoring.service;

import com.example.monitoring.domain.ResponseMessage;
import com.example.monitoring.domain.User;
import com.example.monitoring.domain.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("User Service 테스트")
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    User testUser = null;

    @BeforeEach
    void beforeEach() {
        testUser = User.builder()
                .email("tester@naver.com")
                .password("1q2w3e4r!")
                .name("TESTER 1")
                .userRole(UserRole.USER)
                .build();
    }

    @Order(1)
    @Test
    @DisplayName("회원가입 테스트")
    public void signupTest() throws Exception {
        ResponseMessage result = userService.signup(testUser);

        //then
        Assertions.assertThat(result.isOk()).isEqualTo(true);
        Assertions.assertThat(result.getMessage()).isEqualTo("회원가입에 성공했습니다.");
    }

    @Order(2)
    @Test
    @DisplayName("로그인 테스트")
    public void loginTest() throws Exception {
        ResponseMessage msg = userService.login(testUser.getEmail(), testUser.getPassword());

        Assertions.assertThat(msg.isOk()).isEqualTo(true);
    }

    @Order(3)
    @Test
    @DisplayName("회원 정보 수정 테스트")
    public void updateTest() throws Exception {
        ResponseMessage msg = userService.login(testUser.getEmail(), testUser.getPassword());
        User result = userService.findUserByKey(msg.getMessage());
        result.setName("CHANGED TESTER");

        userService.updateUser(result);

        User updateUser = userService.findUserByKey(msg.getMessage());
        Assertions.assertThat(updateUser.getName()).isEqualTo("CHANGED TESTER");
    }

    @Order(4)
    @Test
    @DisplayName("회원 삭제 테스트")
    public void deleteTest() throws Exception {
        ResponseMessage msg = userService.login(testUser.getEmail(), testUser.getPassword());
        userService.deleteUserByKey(msg.getMessage());

        ResponseMessage deletedMsg = userService.login(testUser.getEmail(), testUser.getPassword());
        Assertions.assertThat(deletedMsg.isOk()).isEqualTo(false);
    }
}