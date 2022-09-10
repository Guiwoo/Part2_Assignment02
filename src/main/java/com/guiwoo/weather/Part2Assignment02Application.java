package com.guiwoo.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class Part2Assignment02Application {

    public static void main(String[] args) {
        SpringApplication.run(Part2Assignment02Application.class, args);
    }

}
