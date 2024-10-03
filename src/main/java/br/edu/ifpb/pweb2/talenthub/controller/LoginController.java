package br.edu.ifpb.pweb2.talenthub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "auth/login";  // Nome do template Thymeleaf
    }
}
