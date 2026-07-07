package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.model.Utilizador;
import com.iefp.clinicaMedica.repository.UtilizadorRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final UtilizadorRepository utilizadorRepository;

    public LoginController(UtilizadorRepository utilizadorRepository) {
        this.utilizadorRepository = utilizadorRepository;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String fazerLogin(
            @RequestParam String email,
            @RequestParam String senha,
            HttpSession session,
            Model model
    ) {
        Optional<Utilizador> utilizadorEncontrado =
                utilizadorRepository.findByEmailAndSenha(email, senha);

        if (utilizadorEncontrado.isEmpty()) {
            model.addAttribute("erro", "Email ou senha inválidos.");
            return "login";
        }

        Utilizador utilizador = utilizadorEncontrado.get();

        session.setAttribute("utilizadorLogado", utilizador);

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String fazerLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}