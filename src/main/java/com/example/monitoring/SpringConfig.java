package com.example.monitoring;

import com.example.monitoring.repository.EquipementRepository;
import com.example.monitoring.repository.IEquipementRepository;
import com.example.monitoring.repository.IUserRepository;
import com.example.monitoring.repository.UserRepository;
import com.example.monitoring.service.EquipmentService;
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

    @Bean
    public EquipmentService equipmentService() { return new EquipmentService(equipmentRepository()); }

    public IUserRepository userRepository() {
        return new UserRepository(dataSource);
    }

    public IEquipementRepository equipmentRepository() { return new EquipementRepository(dataSource);
    }

}
