package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.model.Consulta;
import com.iefp.clinicaMedica.model.Medico;
import com.iefp.clinicaMedica.model.Paciente;
import com.iefp.clinicaMedica.model.Utilizador;
import com.iefp.clinicaMedica.repository.ConsultaRepository;
import com.iefp.clinicaMedica.repository.MedicoRepository;
import com.iefp.clinicaMedica.repository.PacienteRepository;
import com.iefp.clinicaMedica.service.ReceitaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReceitaController {

    private final ReceitaService receitaService;
    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ReceitaController(
            ReceitaService receitaService,
            ConsultaRepository consultaRepository,
            MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository
    ) {
        this.receitaService = receitaService;
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping("/receitas")
    public String listarReceitas(Model model, HttpSession session) {

        Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

        if (utilizador == null) {
            return "redirect:/login";
        }

        String tipo = utilizador.getTipo();

        model.addAttribute("tipoUtilizador", tipo);

        // Secretária vê todas as receitas e todas as consultas
        if (tipo.equals("SECRETARIA")) {
            model.addAttribute("receitas", receitaService.listarTodas());
            model.addAttribute("consultas", consultaRepository.findAll());
            return "receitas";
        }

        // Médico vê apenas receitas associadas a ele
        if (tipo.equals("MEDICO")) {
            Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

            model.addAttribute("receitas", receitaService.listarPorMedico(medico.getId()));
            model.addAttribute("consultas", consultaRepository.findByMedico_IdOrderByDataAscHoraInicioAsc(medico.getId()));
            return "receitas";
        }

        // Paciente vê apenas as suas receitas
        if (tipo.equals("PACIENTE")) {
            Paciente paciente = pacienteRepository.findByUtilizador_Id(utilizador.getId())
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

            model.addAttribute("receitas", receitaService.listarPorPaciente(paciente.getId()));
            return "receitas";
        }

        return "redirect:/acesso-negado";
    }

    @PostMapping("/receitas")
    public String criarReceita(
            @RequestParam Long consultaId,
            @RequestParam String medicamento,
            @RequestParam String dosagem,
            @RequestParam String instrucoes,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Utilizador utilizador = (Utilizador) session.getAttribute("utilizadorLogado");

            if (utilizador == null) {
                return "redirect:/login";
            }

            // Paciente não pode criar receitas
            if (utilizador.getTipo().equals("PACIENTE")) {
                return "redirect:/acesso-negado";
            }

            Consulta consulta = consultaRepository.findById(consultaId)
                    .orElseThrow(() -> new RuntimeException("Consulta não encontrada."));

            // Médico só pode criar receita para consulta dele
            if (utilizador.getTipo().equals("MEDICO")) {
                Medico medico = medicoRepository.findByUtilizador_Id(utilizador.getId())
                        .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

                if (!consulta.getMedico().getId().equals(medico.getId())) {
                    throw new RuntimeException("Não pode criar receita para uma consulta de outro médico.");
                }
            }

            // Secretária pode criar para qualquer consulta
            receitaService.criarReceita(consultaId, medicamento, dosagem, instrucoes);

            return "redirect:/receitas";

        } catch (RuntimeException erro) {
            redirectAttributes.addFlashAttribute("erro", erro.getMessage());
            return "redirect:/receitas";
        }
    }
}