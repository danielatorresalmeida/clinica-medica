package com.iefp.clinicaMedica.controller;

import com.iefp.clinicaMedica.model.Utilizador;
import com.iefp.clinicaMedica.repository.UtilizadorRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class EditarController {

    private final UtilizadorRepository utilizadorRepository;

    public EditarController(UtilizadorRepository utilizadorRepository) {
        this.utilizadorRepository = utilizadorRepository;
    }

    @GetMapping("/utilizadores/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Utilizador utilizador = utilizadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado: " + id));

        model.addAttribute("utilizador", utilizador);

        return "editar-utilizador";
    }

    @PostMapping("/utilizadores/editar/{id}")
    public String guardarAlteracoes(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam(required = false) String senha,
            @RequestParam String tipo,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dataNascimento,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String endereco
    ) {
        Utilizador utilizador = utilizadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado: " + id));

        utilizador.setNome(nome);
        utilizador.setEmail(email);
        utilizador.setTipo(tipo);
        utilizador.setDataNascimento(dataNascimento);
        utilizador.setTelefone(telefone);
        utilizador.setEndereco(endereco);

        if (senha != null && !senha.trim().isEmpty()) {
            utilizador.setSenha(senha);
        }

        utilizadorRepository.save(utilizador);

        return "redirect:/listagem";
    }
}