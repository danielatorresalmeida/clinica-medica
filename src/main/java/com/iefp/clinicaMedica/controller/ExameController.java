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

        if (tipo.equals("ADMIN") || tipo.equals("SECRETARIA")) {
            model.addAttribute("exames", exameService.listarTodos());
            return "exames";
        }

        if (tipo.equals("MEDICO")) {
            Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

            model.addAttribute("exames", exameService.listarPorMedico(medico.getId()));
            return "exames";
        }

        if (tipo.equals("PACIENTE")) {
            Paciente paciente = pacienteRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

            model.addAttribute("exames", exameService.listarPorPaciente(paciente.getId()));
            return "exames";
        }

        return "redirect:/acesso-negado";
    }

    @GetMapping("/exames/novo")
    public String mostrarNovoExame(Model model, HttpSession session) {

        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

        if (utilizador == null) {
            return "redirect:/login";
        }

        String tipo = utilizador.getTipo();
        model.addAttribute("tipoUtilizador", tipo);

        if (tipo.equals("ADMIN") || tipo.equals("SECRETARIA")) {
            model.addAttribute("consultas", consultaRepository.findAll());
            return "novo-exame";
        }

        if (tipo.equals("MEDICO")) {
            Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

            model.addAttribute("consultas", consultaRepository.findByMedico_IdOrderByDataAscHoraInicioAsc(medico.getId()));
            return "novo-exame";
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

            String tipoUtilizador = utilizador.getTipo();

            if (tipoUtilizador.equals("PACIENTE")) {
                return "redirect:/acesso-negado";
            }

            if (!tipoUtilizador.equals("ADMIN")
                    && !tipoUtilizador.equals("SECRETARIA")
                    && !tipoUtilizador.equals("MEDICO")) {
                return "redirect:/acesso-negado";
            }

            Consulta consulta = consultaRepository.findById(consultaId)
                    .orElseThrow(() -> new RuntimeException("Consulta não encontrada."));

            if (tipoUtilizador.equals("MEDICO")) {
                Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                        .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

                if (!consulta.getMedico().getId().equals(medico.getId())) {
                    throw new RuntimeException("Não pode criar exame para uma consulta de outro médico.");
                }
            }

            exameService.criarExame(consultaId, tipo, descricao);

            return "redirect:/exames";

        } catch (RuntimeException erro) {
            redirectAttributes.addFlashAttribute("erro", erro.getMessage());
            return "redirect:/exames/novo";
        }
    }
}