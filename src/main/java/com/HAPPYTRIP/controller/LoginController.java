package com.HAPPYTRIP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/member")
public class LoginController {

    //로그인
    @GetMapping("/login")
    public String LoginForm() {
        return "login";
    }

}