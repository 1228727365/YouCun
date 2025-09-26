package com.api.you_cun.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
public class PageController {
    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/api")
    public String api() {
        return "index";
    }

    @GetMapping("/api/user")
    public String user() {
        return "index";
    }

    @GetMapping("/api/admin")
    public String admin() {
        return "index";
    }

}
