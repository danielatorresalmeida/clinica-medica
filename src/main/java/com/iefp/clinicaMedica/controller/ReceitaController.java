package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.service.ReceitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReceitaController {

    // Serviço responsável pela lógica das receitas
    private final ReceitaService receitaService;

    // Construtor para injetar o serviço
    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    // Mostra a página das receitas
    @GetMapping("/receitas")
    public String listarReceitas(Model model) {

        // Envia as receitas para o HTML
        model.addAttribute("receitas", receitaService.listarTodas());

        // Envia as consultas para o formulário
        model.addAttribute("consultas", receitaService.listarConsultas());

        // Tem de devolver receitas, não exames
        return "receitas";
    }

    // Recebe os dados do formulário e cria uma receita
    @PostMapping("/receitas")
    public String criarReceita(
            @RequestParam Long consultaId,
            @RequestParam String medicamento,
            @RequestParam String dosagem,
            @RequestParam String instrucoes,
            Model model
    ) {
        try {
            // Cria a receita
            receitaService.criarReceita(consultaId, medicamento, dosagem, instrucoes);

            return "redirect:/receitas";

        } catch (RuntimeException erro) {

            // Mostra mensagem de erro
            model.addAttribute("erro", erro.getMessage());

            // Recarrega os dados da página
            model.addAttribute("receitas", receitaService.listarTodas());
            model.addAttribute("consultas", receitaService.listarConsultas());

            return "receitas";
        }
    }
}