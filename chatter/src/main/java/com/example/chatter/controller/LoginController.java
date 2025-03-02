package com.example.chatter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.chatter.form.LoginForm;

@RequestMapping("/login")
@Controller
public class LoginController {

    @GetMapping
    public String showLogin(@ModelAttribute LoginForm form) {
        return "login";
    }
}
