package com.BikkadIT.electronic.store.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/test")
public class HomeController {

    @GetMapping
    public String testing(){
        return "Welcome to electronic store";
    }
}
