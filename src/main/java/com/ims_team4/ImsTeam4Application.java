package com.ims_team4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableScheduling
public class ImsTeam4Application {
    
    public static void main(String[] args) {
        SpringApplication.run(ImsTeam4Application.class, args);
    }

}
