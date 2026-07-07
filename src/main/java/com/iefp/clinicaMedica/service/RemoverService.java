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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RemoverService {

    private final UtilizadorRepository utilizadorRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final SecretariaRepository secretariaRepository;

    public RemoverService(UtilizadorRepository utilizadorRepository,
                          PacienteRepository pacienteRepository,
                          MedicoRepository medicoRepository,
                          SecretariaRepository secretariaRepository) {
        this.utilizadorRepository = utilizadorRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.secretariaRepository = secretariaRepository;
    }

    // Remover paciente
    public void removerPaciente(Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com o ID: " + pacienteId));

        Utilizador utilizador = paciente.getUtilizador();

        pacienteRepository.delete(paciente);
        utilizadorRepository.delete(utilizador);
    }

    // Remover médico
    public void removerMedico(Long medicoId) {
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado com o ID: " + medicoId));

        Utilizador utilizador = medico.getUtilizador();

        medicoRepository.delete(medico);
        utilizadorRepository.delete(utilizador);
    }

    // Remover secretária
    public void removerSecretaria(Long secretariaId) {
        Secretaria secretaria = secretariaRepository.findById(secretariaId)
                .orElseThrow(() -> new RuntimeException("Secretária não encontrada com o ID: " + secretariaId));

        Utilizador utilizador = secretaria.getUtilizador();

        secretariaRepository.delete(secretaria);
        utilizadorRepository.delete(utilizador);
    }
}