package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.model.Medico;
import com.iefp.clinicaMedica.model.Utilizador;
import com.iefp.clinicaMedica.repository.MedicoRepository;
import com.iefp.clinicaMedica.service.DisponibilidadeService;
import com.iefp.clinicaMedica.service.ListagemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class DisponibilidadeController {

    private final DisponibilidadeService disponibilidadeService;
    private final ListagemService listagemService;
    private final MedicoRepository medicoRepository;

    public DisponibilidadeController(
            DisponibilidadeService disponibilidadeService,
            ListagemService listagemService,
            MedicoRepository medicoRepository
    ) {
        this.disponibilidadeService = disponibilidadeService;
        this.listagemService = listagemService;
        this.medicoRepository = medicoRepository;
    }

    @GetMapping("/disponibilidades")
    public String listarDisponibilidades(Model model, HttpSession session) {

        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

        if (utilizador == null) {
            return "redirect:/login";
        }

        String tipo = utilizador.getTipo();
        model.addAttribute("tipoUtilizador", tipo);

        if (tipo.equals("ADMIN") || tipo.equals("SECRETARIA")) {
            model.addAttribute("disponibilidades", disponibilidadeService.listarTodas());
            return "disponibilidades";
        }

        if (tipo.equals("MEDICO")) {
            Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

            model.addAttribute("disponibilidades", disponibilidadeService.listarPorMedico(medico.getId()));
            return "disponibilidades";
        }

        return "redirect:/acesso-negado";
    }

    @GetMapping("/disponibilidades/nova")
    public String mostrarNovaDisponibilidade(Model model, HttpSession session) {

        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

        if (utilizador == null) {
            return "redirect:/login";
        }

        String tipo = utilizador.getTipo();
        model.addAttribute("tipoUtilizador", tipo);

        if (tipo.equals("ADMIN") || tipo.equals("SECRETARIA")) {
            model.addAttribute("medicos", listagemService.listarMedicos());
            return "nova-disponibilidade";
        }

        if (tipo.equals("MEDICO")) {
            return "nova-disponibilidade";
        }

        return "redirect:/acesso-negado";
    }

    @PostMapping("/disponibilidades")
    public String criarDisponibilidades(
            @RequestParam(required = false) Long medicoId,
            @RequestParam LocalDate data,
            @RequestParam LocalTime horaInicioTrabalho,
            @RequestParam LocalTime horaFimTrabalho,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

            if (utilizador == null) {
                return "redirect:/login";
            }

            String tipo = utilizador.getTipo();

            if (tipo.equals("ADMIN") || tipo.equals("SECRETARIA")) {

                if (medicoId == null) {
                    throw new RuntimeException("Selecione um médico.");
                }

                disponibilidadeService.criarDisponibilidade(
                        medicoId,
                        data,
                        horaInicioTrabalho,
                        horaFimTrabalho
                );

                return "redirect:/disponibilidades";
            }

            if (tipo.equals("MEDICO")) {
                Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                        .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

                disponibilidadeService.criarDisponibilidade(
                        medico.getId(),
                        data,
                        horaInicioTrabalho,
                        horaFimTrabalho
                );

                return "redirect:/disponibilidades";
            }

            return "redirect:/acesso-negado";

        } catch (RuntimeException erro) {
            redirectAttributes.addFlashAttribute("erro", erro.getMessage());
            return "redirect:/disponibilidades/nova";
        }
    }
}