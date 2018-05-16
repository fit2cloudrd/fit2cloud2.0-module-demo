package com.fit2cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fit2cloud"})
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
