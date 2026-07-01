package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.repository.PacienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PacienteController {

    // Repositório usado para ir buscar os pacientes à base de dados
    private final PacienteRepository pacienteRepository;

    // Construtor para injetar o repositório
    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    // Mostra a página dos pacientes
    @GetMapping("/pacientes")
    public String listarPacientes(Model model) {

        // Envia a lista de pacientes para o HTML
        model.addAttribute("pacientes", pacienteRepository.findAll());

        // Nome do ficheiro HTML: pacientes.html
        return "pacientes";
    }
}