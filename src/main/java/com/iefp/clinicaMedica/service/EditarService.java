package com.iefp.clinicaMedica.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import com.iefp.clinicaMedica.model.Paciente;
import com.iefp.clinicaMedica.model.Medico;
import com.iefp.clinicaMedica.model.Secretaria;
import com.iefp.clinicaMedica.model.Utilizador;

import com.iefp.clinicaMedica.repository.PacienteRepository;
import com.iefp.clinicaMedica.repository.MedicoRepository;
import com.iefp.clinicaMedica.repository.SecretariaRepository;
import com.iefp.clinicaMedica.repository.UtilizadorRepository;

@Service
@Transactional
public class EditarService {

    private final UtilizadorRepository utilizadorRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final SecretariaRepository secretariaRepository;

    public EditarService(UtilizadorRepository utilizadorRepository,
                         PacienteRepository pacienteRepository,
                         MedicoRepository medicoRepository,
                         SecretariaRepository secretariaRepository) {
        this.utilizadorRepository = utilizadorRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
        this.secretariaRepository = secretariaRepository;
    }

    // Editar paciente
    public void editarPaciente(Long pacienteId,
                               String nome,
                               String email,
                               String senha,
                               LocalDate dataNascimento,
                               String telefone,
                               String endereco) {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com o ID: " + pacienteId));

        Utilizador utilizador = paciente.getUtilizador();

        utilizador.setNome(nome);
        utilizador.setEmail(email);
        utilizador.setSenha(senha);
        utilizador.setDataNascimento(dataNascimento);
        utilizador.setTelefone(telefone);
        utilizador.setEndereco(endereco);

        utilizadorRepository.save(utilizador);
        pacienteRepository.save(paciente);
    }

    // Editar médico
    public void editarMedico(Long medicoId,
                             String nome,
                             String email,
                             String senha,
                             String especialidade,
                             String telefone,
                             String endereco) {

        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado com o ID: " + medicoId));

        Utilizador utilizador = medico.getUtilizador();

        utilizador.setNome(nome);
        utilizador.setEmail(email);
        utilizador.setSenha(senha);
        utilizador.setTelefone(telefone);
        utilizador.setEndereco(endereco);

        medico.setEspecialidade(especialidade);

        utilizadorRepository.save(utilizador);
        medicoRepository.save(medico);
    }

    // Editar secretária
    public void editarSecretaria(Long secretariaId,
                                 String nome,
                                 String email,
                                 String senha,
                                 String telefone,
                                 String endereco) {

        Secretaria secretaria = secretariaRepository.findById(secretariaId)
                .orElseThrow(() -> new RuntimeException("Secretária não encontrada com o ID: " + secretariaId));

        Utilizador utilizador = secretaria.getUtilizador();

        utilizador.setNome(nome);
        utilizador.setEmail(email);
        utilizador.setSenha(senha);
        utilizador.setTelefone(telefone);
        utilizador.setEndereco(endereco);

        utilizadorRepository.save(utilizador);
        secretariaRepository.save(secretaria);
    }
}