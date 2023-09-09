package com.example.monitoring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MonitoringApplication {
	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}
}