package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.service.RegistoService;
import org.springframework.stereotype.Controller;

@Controller
public class PacienteController {

    private final RegistoService registoService;

    public PacienteController(RegistoService registoService) {
        this.registoService = registoService;
    }
}