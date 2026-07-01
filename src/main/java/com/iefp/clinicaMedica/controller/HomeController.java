package com.iefp.clinicaMedica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Mostra a página principal da aplicação
    @GetMapping("/")
    public String paginaPrincipal() {
        return "index";
    }

    // Mostra página de acesso negado
    @GetMapping("/acesso-negado")
    public String acessoNegado() {
    return "acesso-negado";
}
}