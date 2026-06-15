package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.model.Paciente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PacienteContoller {

    private List<Paciente> pacientes = new ArrayList<>();

    @GetMapping("/pacientes")
    public String listaPacientes(Model model){
        model.addAttribute("lista", pacientes);
        return "/pacientes";
    }

    @PostMapping("/pacientes")
    public String adicionarPaciente(@RequestParam String nome,
                                    @RequestParam Integer idade){
        Paciente paciente = new Paciente(nome, idade);
        pacientes.add(paciente);

        return "redirect:/pacientes";
    }
}
