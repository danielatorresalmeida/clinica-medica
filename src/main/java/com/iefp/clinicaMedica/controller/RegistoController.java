package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.service.RegistoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RegistoController {

    private final RegistoService registoService;

    public RegistoController(RegistoService registoService) {
        this.registoService = registoService;
    }

    @GetMapping("/")
    public String paginaInicial() {
        return "redirect:/registar-utilizador";
    }

    @GetMapping("/registar-utilizador")
    public String mostrarFormularioRegisto() {
        return "registar-utilizador";
    }

    @PostMapping("/registo/utilizador")
    public String registarUtilizador(@RequestParam String tipo,
                                      @RequestParam String nome,
                                      @RequestParam String email,
                                      @RequestParam String senha,
                                      @RequestParam String dataNascimento,
                                      @RequestParam String telefone,
                                      @RequestParam String endereco,
                                      @RequestParam(required = false) String especialidade) {

        LocalDate dataNascimentoConvertida = LocalDate.parse(dataNascimento);

        if (tipo.equals("PACIENTE")) {
            registoService.registarPaciente(
                    nome,
                    email,
                    senha,
                    dataNascimentoConvertida,
                    telefone,
                    endereco
            );
        } else if (tipo.equals("MEDICO")) {
            registoService.registarMedico(
                    nome,
                    email,
                    senha,
                    dataNascimentoConvertida,
                    telefone,
                    endereco,
                    especialidade
            );
        } else if (tipo.equals("SECRETARIA")) {
            registoService.registarSecretaria(
                    nome,
                    email,
                    senha,
                    dataNascimentoConvertida,
                    telefone,
                    endereco
            );
        }

        return "redirect:/listagem";
    }
}