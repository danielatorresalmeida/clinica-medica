package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.repository.EspecialidadeRepository;
import com.iefp.clinicaMedica.service.RegistoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RegistoController {

    // Serviço responsável por registar utilizadores
    private final RegistoService registoService;

    // Repositório das especialidades
    private final EspecialidadeRepository especialidadeRepository;

    // Construtor para injetar dependências
    public RegistoController(
            RegistoService registoService,
            EspecialidadeRepository especialidadeRepository
    ) {
        this.registoService = registoService;
        this.especialidadeRepository = especialidadeRepository;
    }

    // Mostra o formulário de registo
    @GetMapping({"/registar-utilizador", "/registar"})
    public String mostrarFormularioRegisto(Model model) {

        // Envia as especialidades para o HTML
        model.addAttribute("especialidades", especialidadeRepository.findAllByOrderByNomeAsc());

        return "registar-utilizador";
    }

    // Recebe os dados do formulário e cria o utilizador
    @PostMapping("/registo/utilizador")
    public String registarUtilizador(
            @RequestParam String tipo,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String dataNascimento,
            @RequestParam String telefone,
            @RequestParam String endereco,
            @RequestParam(required = false) String especialidade
    ) {

        // Converte a data para LocalDate
        LocalDate dataNascimentoConvertida = LocalDate.parse(dataNascimento);

        // Regista paciente
        if (tipo.equals("PACIENTE")) {
            registoService.registarPaciente(
                    nome,
                    email,
                    senha,
                    dataNascimentoConvertida,
                    telefone,
                    endereco
            );

        // Regista médico
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

        // Regista secretária
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