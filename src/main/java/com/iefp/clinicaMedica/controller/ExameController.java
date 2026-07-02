package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.model.Consulta;
import com.iefp.clinicaMedica.model.Medico;
import com.iefp.clinicaMedica.model.Paciente;
import com.iefp.clinicaMedica.model.Utilizador;
import com.iefp.clinicaMedica.repository.ConsultaRepository;
import com.iefp.clinicaMedica.repository.MedicoRepository;
import com.iefp.clinicaMedica.repository.PacienteRepository;
import com.iefp.clinicaMedica.service.ExameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExameController {

    private final ExameService exameService;
    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ExameController(
            ExameService exameService,
            ConsultaRepository consultaRepository,
            MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository
    ) {
        this.exameService = exameService;
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping("/exames")
    public String listarExames(Model model, HttpSession session) {

        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

        if (utilizador == null) {
            return "redirect:/login";
        }

        String tipo = utilizador.getTipo();

        model.addAttribute("tipoUtilizador", tipo);

        // Secretária vê todos os exames e todas as consultas
        if (tipo.equals("SECRETARIA")) {
            model.addAttribute("exames", exameService.listarTodos());
            model.addAttribute("consultas", consultaRepository.findAll());
            return "exames";
        }

        // Médico vê apenas exames associados a ele
        if (tipo.equals("MEDICO")) {
            Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

            model.addAttribute("exames", exameService.listarPorMedico(medico.getId()));
            model.addAttribute("consultas", consultaRepository.findByMedico_IdOrderByDataAscHoraInicioAsc(medico.getId()));
            return "exames";
        }

        // Paciente vê apenas os seus exames
        if (tipo.equals("PACIENTE")) {
            Paciente paciente = pacienteRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

            model.addAttribute("exames", exameService.listarPorPaciente(paciente.getId()));
            return "exames";
        }

        return "redirect:/acesso-negado";
    }

    @PostMapping("/exames")
    public String criarExame(
            @RequestParam Long consultaId,
            @RequestParam String tipo,
            @RequestParam String descricao,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

            if (utilizador == null) {
                return "redirect:/login";
            }

            // Paciente não pode criar exames
            if (utilizador.getTipo().equals("PACIENTE")) {
                return "redirect:/acesso-negado";
            }

            Consulta consulta = consultaRepository.findById(consultaId)
                    .orElseThrow(() -> new RuntimeException("Consulta não encontrada."));

            // Médico só pode criar exame para consulta dele
            if (utilizador.getTipo().equals("MEDICO")) {
                Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                        .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

                if (!consulta.getMedico().getId().equals(medico.getId())) {
                    throw new RuntimeException("Não pode criar exame para uma consulta de outro médico.");
                }
            }

            // Secretária pode criar para qualquer consulta
            exameService.criarExame(consultaId, tipo, descricao);

            return "redirect:/exames";

        } catch (RuntimeException erro) {
            redirectAttributes.addFlashAttribute("erro", erro.getMessage());
            return "redirect:/exames";
        }
    }
}