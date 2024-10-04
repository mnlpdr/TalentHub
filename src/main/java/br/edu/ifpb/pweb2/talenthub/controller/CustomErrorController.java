package br.edu.ifpb.pweb2.talenthub.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Retorne o nome do template da página de erro personalizada
        return "auth/error"; 
    }
}