package com.gamecrew.gamecrew_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GameCrewProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameCrewProjectApplication.class, args);
    }
}
