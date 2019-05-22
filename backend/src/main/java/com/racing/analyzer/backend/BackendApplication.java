package com.racing.analyzer.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BackendApplication {

    // Swagger-UI: http://localhost:8080/swagger-ui.html
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
