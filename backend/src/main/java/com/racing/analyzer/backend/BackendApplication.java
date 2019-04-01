package com.racing.analyzer.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendApplication {

    //TODO: remove create and update commands and work directly on dtos
    //TODO: add a interface on for the services with extended functionality
    //TODO: look into paging the timings results.
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
