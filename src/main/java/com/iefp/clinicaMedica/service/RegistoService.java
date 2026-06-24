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

import java.time.LocalDate;

@Service
@Transactional
public class RegistoService {

    private final UtilizadorRepository utilizadorRepository;
    private final PacienteRepository pacienteRepository;
    private final SecretariaRepository secretariaRepository;
    private final MedicoRepository medicoRepository;

    public RegistoService(UtilizadorRepository utilizadorRepository,
                          PacienteRepository pacienteRepository,
                          SecretariaRepository secretariaRepository,
                          MedicoRepository medicoRepository) {
        this.utilizadorRepository = utilizadorRepository;
        this.pacienteRepository = pacienteRepository;
        this.secretariaRepository = secretariaRepository;
        this.medicoRepository = medicoRepository;
    }

    // Registar Paciente
    public Paciente registarPaciente(String nome,
                                     String email,
                                     String senha,
                                     LocalDate dataNascimento,
                                     String telefone,
                                     String endereco) {

        Utilizador utilizador = new Utilizador(
                null,
                nome,
                email,
                senha,
                "PACIENTE",
                dataNascimento,
                telefone,
                endereco
        );

        // Primeiro guarda o utilizador
        utilizador = utilizadorRepository.save(utilizador);

        // Depois cria e guarda o paciente associado ao utilizador
        Paciente paciente = new Paciente(
                null,
                utilizador
        );

        return pacienteRepository.save(paciente);
    }

    // Registar Médico
    public Medico registarMedico(String nome,
                                 String email,
                                 String senha,
                                 LocalDate dataNascimento,
                                 String telefone,
                                 String endereco,
                                 String especialidade) {

        Utilizador utilizador = new Utilizador(
                null,
                nome,
                email,
                senha,
                "MEDICO",
                dataNascimento,
                telefone,
                endereco
        );

        // Primeiro guarda o utilizador
        utilizador = utilizadorRepository.save(utilizador);

        // Depois cria e guarda o médico associado ao utilizador
        Medico medico = new Medico(
                null,
                especialidade,
                utilizador
        );

        return medicoRepository.save(medico);
    }

    // Registar Secretária
    public Secretaria registarSecretaria(String nome,
                                         String email,
                                         String senha,
                                         LocalDate dataNascimento,
                                         String telefone,
                                         String endereco) {

        Utilizador utilizador = new Utilizador(
                null,
                nome,
                email,
                senha,
                "SECRETARIA",
                dataNascimento,
                telefone,
                endereco
        );

        // Primeiro guarda o utilizador
        utilizador = utilizadorRepository.save(utilizador);

        // Depois cria e guarda a secretária associada ao utilizador
        Secretaria secretaria = new Secretaria(
                null,
                utilizador
        );

        return secretariaRepository.save(secretaria);
    }
}