package com.rpm.web;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableBatchProcessing
@EnableWebSecurity
@EnableScheduling
public class RpmApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RpmApplication.class, args);
    }
}
