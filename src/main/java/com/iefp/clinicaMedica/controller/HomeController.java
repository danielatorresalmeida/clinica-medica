package com.iefp.clinicaMedica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "acesso-negado";
    }
}