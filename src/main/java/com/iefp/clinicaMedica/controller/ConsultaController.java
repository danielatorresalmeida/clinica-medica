package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.model.Medico;
import com.iefp.clinicaMedica.model.Paciente;
import com.iefp.clinicaMedica.model.Utilizador;
import com.iefp.clinicaMedica.repository.MedicoRepository;
import com.iefp.clinicaMedica.repository.PacienteRepository;
import com.iefp.clinicaMedica.service.ConsultaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ConsultaController {

    private final ConsultaService consultaService;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaController(
            ConsultaService consultaService,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository
    ) {
        this.consultaService = consultaService;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @GetMapping("/consultas")
    public String listarConsultas(Model model, HttpSession session) {

        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

        if (utilizador == null) {
            return "redirect:/login";
        }

        String tipo = utilizador.getTipo();
        model.addAttribute("tipoUtilizador", tipo);

        if (tipo.equals("ADMIN") || tipo.equals("SECRETARIA")) {
            model.addAttribute("consultas", consultaService.listarTodas());
            return "consultas";
        }

        if (tipo.equals("MEDICO")) {
            Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

            model.addAttribute("consultas", consultaService.listarPorMedico(medico.getId()));
            return "consultas";
        }

        if (tipo.equals("PACIENTE")) {
            Paciente paciente = pacienteRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

            model.addAttribute("consultas", consultaService.listarPorPaciente(paciente.getId()));
            return "consultas";
        }

        return "redirect:/acesso-negado";
    }

    @GetMapping("/consultas/nova")
    public String mostrarNovaConsulta(Model model, HttpSession session) {

        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

        if (utilizador == null) {
            return "redirect:/login";
        }

        String tipo = utilizador.getTipo();
        model.addAttribute("tipoUtilizador", tipo);

        if (tipo.equals("ADMIN") || tipo.equals("SECRETARIA")) {
            model.addAttribute("pacientes", consultaService.listarPacientes());
            model.addAttribute("disponibilidadesLivres", consultaService.listarDisponibilidadesLivres());
            return "nova-consulta";
        }

        if (tipo.equals("PACIENTE")) {
            model.addAttribute("disponibilidadesLivres", consultaService.listarDisponibilidadesLivres());
            return "nova-consulta";
        }

        return "redirect:/acesso-negado";
    }

    @PostMapping("/consultas")
    public String marcarConsulta(
            @RequestParam(required = false) Long pacienteId,
            @RequestParam Long disponibilidadeId,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

            if (utilizador == null) {
                return "redirect:/login";
            }

            String tipo = utilizador.getTipo();

            if (tipo.equals("PACIENTE")) {
                Paciente paciente = pacienteRepository.findByUtilizador_Id(utilizador.getId())
                        .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

                consultaService.marcarConsulta(paciente.getId(), disponibilidadeId);
                return "redirect:/consultas";
            }

            if (tipo.equals("ADMIN") || tipo.equals("SECRETARIA")) {
                if (pacienteId == null) {
                    throw new RuntimeException("Selecione um paciente.");
                }

                consultaService.marcarConsulta(pacienteId, disponibilidadeId);
                return "redirect:/consultas";
            }

            return "redirect:/acesso-negado";

        } catch (RuntimeException erro) {
            redirectAttributes.addFlashAttribute("erro", erro.getMessage());
            return "redirect:/consultas/nova";
        }
    }
}