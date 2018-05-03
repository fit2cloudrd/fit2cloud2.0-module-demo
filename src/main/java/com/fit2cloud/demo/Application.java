package com.fit2cloud.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = {"file:/opt/fit2cloud/fit2cloud.properties"}, encoding = "UTF-8", ignoreResourceNotFound = true)
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
