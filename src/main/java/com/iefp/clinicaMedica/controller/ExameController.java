package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.service.ExameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExameController {

    // Serviço responsável pela lógica dos exames
    private final ExameService exameService;

    // Construtor para injetar o serviço
    public ExameController(ExameService exameService) {
        this.exameService = exameService;
    }

    // Mostra a página dos exames
    @GetMapping("/exames")
    public String listarExames(Model model) {

        // Envia os exames para o HTML
        model.addAttribute("exames", exameService.listarTodos());

        // Envia as consultas para o formulário
        model.addAttribute("consultas", exameService.listarConsultas());

        return "exames";
    }

    // Recebe os dados do formulário e cria o exame
    @PostMapping("/exames")
    public String criarExame(
            @RequestParam Long consultaId,
            @RequestParam String tipo,
            @RequestParam String descricao,
            Model model
    ) {
        try {
            // Chama o serviço para criar o exame
            exameService.criarExame(consultaId, tipo, descricao);

            return "redirect:/exames";

        } catch (RuntimeException erro) {

            // Mostra erro se algo correr mal
            model.addAttribute("erro", erro.getMessage());
            model.addAttribute("exames", exameService.listarTodos());
            model.addAttribute("consultas", exameService.listarConsultas());

            return "exames";
        }
    }
}