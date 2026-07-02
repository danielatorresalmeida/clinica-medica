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

    // Repositório usado para procurar utilizadores na base de dados
    private final UtilizadorRepository utilizadorRepository;

    // Construtor para injetar o repositório
    public LoginController(UtilizadorRepository utilizadorRepository) {
        this.utilizadorRepository = utilizadorRepository;
    }

    // Mostra a página de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // Recebe os dados do formulário de login
    @PostMapping("/login")
    public String fazerLogin(
            @RequestParam String email,
            @RequestParam String senha,
            HttpSession session,
            Model model
    ) {
        // Procura o utilizador pelo email e senha
        Optional<Utilizador> utilizadorEncontrado =
                utilizadorRepository.findByEmailAndSenha(email, senha);

        // Se não encontrar, volta para o login com mensagem de erro
        if (utilizadorEncontrado.isEmpty()) {
            model.addAttribute("erro", "Email ou senha inválidos.");
            return "login";
        }

        // Guarda o utilizador encontrado numa variável
        Utilizador utilizador = utilizadorEncontrado.get();

        // Guarda o utilizador na sessão
        session.setAttribute("utilizadorLogado", utilizador);

        // Redireciona conforme o tipo de utilizador
        if (utilizador.getTipo().equals("ADMIN")) {
            return "redirect:/listagem";
        }

        if (utilizador.getTipo().equals("SECRETARIA")) {
            return "redirect:/listagem";
        }

        if (utilizador.getTipo().equals("MEDICO")) {
            return "redirect:/consultas";
        }

        if (utilizador.getTipo().equals("PACIENTE")) {
            return "redirect:/consultas";
        }

        // Caso o tipo não seja reconhecido
        return "redirect:/login";
    }

    // Faz logout do utilizador
    @GetMapping("/logout")
    public String fazerLogout(HttpSession session) {

        // Limpa a sessão
        session.invalidate();

        // Depois do logout, volta para o login
        return "redirect:/login";
    }
}