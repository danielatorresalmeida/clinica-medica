package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.service.RemoverService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RemoverController {

    private final RemoverService removerService;

    public RemoverController(RemoverService removerService) {
        this.removerService = removerService;
    }

    @GetMapping("/remover/paciente/{id}")
    public String removerPaciente(@PathVariable("id") Long pacienteId) {
        removerService.removerPaciente(pacienteId);
        return "redirect:/listar/pacientes";
    }

    @GetMapping("/remover/medico/{id}")
    public String removerMedico(@PathVariable("id") Long medicoId) {
        removerService.removerMedico(medicoId);
        return "redirect:/listar/medicos";
    }

    @GetMapping("/remover/secretaria/{id}")
    public String removerSecretaria(@PathVariable("id") Long secretariaId) {
        removerService.removerSecretaria(secretariaId);
        return "redirect:/listar/secretarias";
    }
}