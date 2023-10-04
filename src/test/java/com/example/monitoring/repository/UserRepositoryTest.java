package com.example.monitoring.repository;

import com.example.monitoring.domain.User;
import com.example.monitoring.domain.UserRole;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class UserRepositoryTest {
    @Autowired
    private IUserRepository userRepository;

    @Test
    @DisplayName("Database 유저 저장")
    public void saveTest() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("Database Email로 유저 찾기")
    public void findByEmailTest() {
       //given
        User testUser = User.builder()
               .email("test1@naver.com")
               .password("1q2w3e4r!")
               .name("TESTER 1")
               .userRole(UserRole.USER)
               .user_no(123)
               .build();

        //when
        Optional<User> result = userRepository.findByEmail("admin@naver.com");

        //then
        Assertions.assertThat(testUser.getUser_no()).isEqualTo(result.orElseGet(() -> new User()).getUser_no());
    }
}
