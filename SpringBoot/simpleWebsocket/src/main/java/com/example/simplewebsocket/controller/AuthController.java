package com.example.simplewebsocket.controller;

import com.example.simplewebsocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/")
    public String home() {
        return "index"; // templates/index.html
    }

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin(Model model, CsrfToken token) {
        model.addAttribute("_csrf", token);

        return "login"; // templates/login.html
    }

    @GetMapping("/signIn")
    public String GetSignIn(Model model, CsrfToken token) {
        model.addAttribute("_csrf", token);
        return "signin";
    }

    @PostMapping("/signIn")
    public String PostSignIn(@RequestParam String username,@RequestParam String password) {
        userService.register(username,password);

        return "redirect:/";
    }

    @GetMapping("/chat")
    public String chat() {

        return "chat";
    }

}
