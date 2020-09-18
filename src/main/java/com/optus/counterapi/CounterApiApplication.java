package com.optus.counterapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.optus.counterapi.entity"})
@EnableJpaRepositories(basePackages = {"com.optus.counterapi.repository"})
public class CounterApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CounterApiApplication.class, args);
    }

}
