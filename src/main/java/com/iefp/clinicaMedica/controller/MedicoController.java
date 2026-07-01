package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.repository.MedicoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MedicoController {

    // Repositório usado para ir buscar os médicos à base de dados
    private final MedicoRepository medicoRepository;

    // Construtor para injetar o repositório
    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    // Mostra a página dos médicos
    @GetMapping("/medicos")
    public String listarMedicos(Model model) {

        // Envia a lista de médicos para o HTML
        model.addAttribute("medicos", medicoRepository.findAll());

        // Nome do ficheiro HTML: medicos.html
        return "medicos";
    }
}