package com.HAPPYTRIP.controller;

import com.HAPPYTRIP.dto.AirForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(AirForm airForm) {
        System.out.println("+++++++++++++++++++++");
        return "home";
    }


}