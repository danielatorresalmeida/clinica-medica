package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.repository.SecretariaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecretariaController {

    private final SecretariaRepository secretariaRepository;

    public SecretariaController(SecretariaRepository secretariaRepository) {
        this.secretariaRepository = secretariaRepository;
    }

    @GetMapping("/secretarias")
    public String listarSecretarias(Model model) {
        model.addAttribute("secretarias", secretariaRepository.findAll());
        return "secretarias";
    }
}