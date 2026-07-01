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

@Controller
public class ConsultaController {

    // Serviço das consultas
    private final ConsultaService consultaService;

    // Repositório dos pacientes
    private final PacienteRepository pacienteRepository;

    // Repositório dos médicos
    private final MedicoRepository medicoRepository;

    // Construtor para injetar dependências
    public ConsultaController(
            ConsultaService consultaService,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository
    ) {
        this.consultaService = consultaService;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    // Mostra a página das consultas
    @GetMapping("/consultas")
    public String listarConsultas(Model model, HttpSession session) {

        // Vai buscar o utilizador logado
        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

        // Se não houver utilizador logado, volta para o login
        if (utilizador == null) {
            return "redirect:/login";
        }

        // Secretária vê todas as consultas
        if (utilizador.getTipo().equals("SECRETARIA")) {
            model.addAttribute("consultas", consultaService.listarTodas());
            model.addAttribute("pacientes", consultaService.listarPacientes());
            model.addAttribute("disponibilidadesLivres", consultaService.listarDisponibilidadesLivres());
            model.addAttribute("tipoUtilizador", "SECRETARIA");
            return "consultas";
        }

        // Médico vê apenas as suas consultas
        if (utilizador.getTipo().equals("MEDICO")) {
            Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

            model.addAttribute("consultas", consultaService.listarPorMedico(medico.getId()));
            model.addAttribute("tipoUtilizador", "MEDICO");
            return "consultas";
        }

        // Paciente vê apenas as suas consultas
        if (utilizador.getTipo().equals("PACIENTE")) {
            Paciente paciente = pacienteRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

            model.addAttribute("consultas", consultaService.listarPorPaciente(paciente.getId()));
            model.addAttribute("disponibilidadesLivres", consultaService.listarDisponibilidadesLivres());
            model.addAttribute("tipoUtilizador", "PACIENTE");
            return "consultas";
        }

        return "redirect:/acesso-negado";
    }

    // Marca consulta
    @PostMapping("/consultas")
    public String marcarConsulta(
            @RequestParam(required = false) Long pacienteId,
            @RequestParam Long disponibilidadeId,
            HttpSession session,
            Model model
    ) {
        try {
            // Vai buscar o utilizador logado
            Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

            if (utilizador == null) {
                return "redirect:/login";
            }

            // Se for paciente, usa automaticamente o próprio paciente
            if (utilizador.getTipo().equals("PACIENTE")) {
                Paciente paciente = pacienteRepository.findByUtilizador_Id(utilizador.getId())
                        .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

                consultaService.marcarConsulta(paciente.getId(), disponibilidadeId);
                return "redirect:/consultas";
            }

            // Se for secretária, pode escolher o paciente no formulário
            if (utilizador.getTipo().equals("SECRETARIA")) {
                consultaService.marcarConsulta(pacienteId, disponibilidadeId);
                return "redirect:/consultas";
            }

            // Médico não marca consulta neste fluxo
            return "redirect:/acesso-negado";

        } catch (RuntimeException erro) {
            model.addAttribute("erro", erro.getMessage());
            return "redirect:/consultas";
        }
    }
}