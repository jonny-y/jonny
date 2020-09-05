package com.jonny.spring.boot.blog.initializrstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/users")
public class Application {

    @GetMapping
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
