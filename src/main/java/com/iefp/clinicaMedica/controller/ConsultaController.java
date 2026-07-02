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

        // Secretária vê todas as consultas e pode marcar para qualquer paciente
        if (tipo.equals("SECRETARIA")) {
            model.addAttribute("consultas", consultaService.listarTodas());
            model.addAttribute("pacientes", consultaService.listarPacientes());
            model.addAttribute("disponibilidadesLivres", consultaService.listarDisponibilidadesLivres());
            return "consultas";
        }

        // Médico vê apenas as suas consultas
        if (tipo.equals("MEDICO")) {
            Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

            model.addAttribute("consultas", consultaService.listarPorMedico(medico.getId()));
            return "consultas";
        }

        // Paciente vê apenas as suas consultas e pode marcar consulta para si próprio
        if (tipo.equals("PACIENTE")) {
            Paciente paciente = pacienteRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

            model.addAttribute("consultas", consultaService.listarPorPaciente(paciente.getId()));
            model.addAttribute("disponibilidadesLivres", consultaService.listarDisponibilidadesLivres());
            return "consultas";
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

            // Paciente marca consulta apenas para si próprio
            if (utilizador.getTipo().equals("PACIENTE")) {
                Paciente paciente = pacienteRepository.findByUtilizador_Id(utilizador.getId())
                        .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

                consultaService.marcarConsulta(paciente.getId(), disponibilidadeId);
                return "redirect:/consultas";
            }

            // Secretária pode escolher qualquer paciente
            if (utilizador.getTipo().equals("SECRETARIA")) {
                consultaService.marcarConsulta(pacienteId, disponibilidadeId);
                return "redirect:/consultas";
            }

            // Médico não marca consulta neste fluxo
            return "redirect:/acesso-negado";

        } catch (RuntimeException erro) {
            redirectAttributes.addFlashAttribute("erro", erro.getMessage());
            return "redirect:/consultas";
        }
    }
}