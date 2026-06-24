package com.iefp.clinicaMedica.service;

import com.iefp.clinicaMedica.model.Medico;
import com.iefp.clinicaMedica.model.Paciente;
import com.iefp.clinicaMedica.model.Secretaria;
import com.iefp.clinicaMedica.model.Utilizador;
import com.iefp.clinicaMedica.repository.MedicoRepository;
import com.iefp.clinicaMedica.repository.PacienteRepository;
import com.iefp.clinicaMedica.repository.SecretariaRepository;
import com.iefp.clinicaMedica.repository.UtilizadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListagemService {

    private final UtilizadorRepository utilizadorRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final SecretariaRepository secretariaRepository;

    public ListagemService(UtilizadorRepository utilizadorRepository,
                           PacienteRepository pacienteRepository,
                           MedicoRepository medicoRepository,
                           SecretariaRepository secretariaRepository) {
        this.utilizadorRepository = utilizadorRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.secretariaRepository = secretariaRepository;
    }

    public List<Utilizador> listarUtilizadores() {
        return utilizadorRepository.findAll();
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public List<Medico> listarMedicos() {
        return medicoRepository.findAll();
    }

    public List<Secretaria> listarSecretarias() {
        return secretariaRepository.findAll();
    }
}