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
    public String listarDisponibilidade(Model model, HttpSession session) {

        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

        if (utilizador == null) {
            return "redirect:/login";
        }

        String tipo = utilizador.getTipo();

        model.addAttribute("tipoUtilizador", tipo);

        // Secretária vê todas as disponibilidades e pode escolher qualquer médico
        if (tipo.equals("SECRETARIA")) {
            model.addAttribute("disponibilidades", disponibilidadeService.listarTodas());
            model.addAttribute("medicos", listagemService.listarMedicos());
            return "disponibilidades";
        }

        // Médico vê apenas as suas disponibilidades
        if (tipo.equals("MEDICO")) {
            Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

            model.addAttribute("disponibilidades", disponibilidadeService.listarPorMedico(medico.getId()));
            return "disponibilidades";
        }

        // Paciente não gere disponibilidades
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

            // Secretária pode criar disponibilidade para qualquer médico
            if (utilizador.getTipo().equals("SECRETARIA")) {

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

            // Médico só pode criar disponibilidade para si próprio
            if (utilizador.getTipo().equals("MEDICO")) {
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
            return "redirect:/disponibilidades";
        }
    }
}