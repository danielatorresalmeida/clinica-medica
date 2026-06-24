package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.service.ListagemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListagemController {

    private final ListagemService listagemService;

    public ListagemController(ListagemService listagemService) {
        this.listagemService = listagemService;
    }

    @GetMapping("/listagem")
    public String mostrarListagem(Model model) {
        model.addAttribute("utilizadores", listagemService.listarUtilizadores());
        model.addAttribute("pacientes", listagemService.listarPacientes());
        model.addAttribute("medicos", listagemService.listarMedicos());
        model.addAttribute("secretarias", listagemService.listarSecretarias());

        return "listagem";
    }
}