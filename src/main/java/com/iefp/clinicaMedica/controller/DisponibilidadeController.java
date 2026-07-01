package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.service.DisponibilidadeService;
import com.iefp.clinicaMedica.service.ListagemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class DisponibilidadeController {

    // Serviço responsável pela lógica das disponibilidades
    private final DisponibilidadeService disponibilidadeService;

    // Serviço usado para listar médicos no formulário
    private final ListagemService listagemService;

    // Construtor para injetar os serviços
    public DisponibilidadeController(
            DisponibilidadeService disponibilidadeService,
            ListagemService listagemService
    ) {
        this.disponibilidadeService = disponibilidadeService;
        this.listagemService = listagemService;
    }

    // Mostra a página de disponibilidades
    @GetMapping("/disponibilidades")
    public String listarDisponibilidade(Model model) {

        // Envia a lista de disponibilidades para o HTML
        model.addAttribute("disponibilidades", disponibilidadeService.listarTodas());

        // Envia a lista de médicos para o select do formulário
        model.addAttribute("medicos", listagemService.listarMedicos());

        // Nome do ficheiro HTML, disponibilidades.html
        return "disponibilidades";
    }

    // Recebe os dados do formulário e cria disponibilidades
    @PostMapping("/disponibilidades")
    public String criarDisponibilidades(
            @RequestParam Long medicoId,
            @RequestParam LocalDate data,
            @RequestParam LocalTime horaInicioTrabalho,
            @RequestParam LocalTime horaFimTrabalho,
            Model model
    ) {
        try {
            // Chama o serviço para criar as disponibilidades
            disponibilidadeService.criarDisponibilidade(
                    medicoId,
                    data,
                    horaInicioTrabalho,
                    horaFimTrabalho
            );

            // Depois de criar, volta para a página de disponibilidades
            return "redirect:/disponibilidades";

        } catch (RuntimeException erro) {

            // Se der erro, envia a mensagem de erro para o HTML
            model.addAttribute("erro", erro.getMessage());

            // Reenvia os dados necessários para a página não ficar vazia
            model.addAttribute("disponibilidades", disponibilidadeService.listarTodas());
            model.addAttribute("medicos", listagemService.listarMedicos());

            // Volta para a mesma página
            return "disponibilidades";
        }
    }
}