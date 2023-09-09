package com.example.monitoring;

import com.example.monitoring.repository.IUserRepository;
import com.example.monitoring.repository.JdbcUserRepository;
import com.example.monitoring.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Bean
    public UserService userService(){
        return new UserService(userRepository());
    }

    public IUserRepository userRepository() {
        return new JdbcUserRepository(dataSource);
    }

}
