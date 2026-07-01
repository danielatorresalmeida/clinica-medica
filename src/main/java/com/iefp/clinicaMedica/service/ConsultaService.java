package com.iefp.clinicaMedica.service;

import com.iefp.clinicaMedica.model.Consulta;
import com.iefp.clinicaMedica.model.Disponibilidade;
import com.iefp.clinicaMedica.model.Paciente;
import com.iefp.clinicaMedica.repository.ConsultaRepository;
import com.iefp.clinicaMedica.repository.DisponibilidadeRepository;
import com.iefp.clinicaMedica.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    // Repositório das consultas
    private final ConsultaRepository consultaRepository;

    // Repositório dos pacientes
    private final PacienteRepository pacienteRepository;

    // Repositório das disponibilidades
    private final DisponibilidadeRepository disponibilidadeRepository;

    // Construtor para injetar os repositórios
    public ConsultaService(
            ConsultaRepository consultaRepository,
            PacienteRepository pacienteRepository,
            DisponibilidadeRepository disponibilidadeRepository
    ) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.disponibilidadeRepository = disponibilidadeRepository;
    }

    // Lista todas as consultas
    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    // Lista todos os pacientes
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    // Lista consultas de um paciente específico
    public List<Consulta> listarPorPaciente(Long pacienteId) {
    return consultaRepository.findByPaciente_IdOrderByDataAscHoraInicioAsc(pacienteId);
    }

    // Lista consultas de um médico específico
    public List<Consulta> listarPorMedico(Long medicoId) {
    return consultaRepository.findByMedico_IdOrderByDataAscHoraInicioAsc(medicoId);
    }

    // Lista apenas disponibilidades livres
    public List<Disponibilidade> listarDisponibilidadesLivres() {
        return disponibilidadeRepository.findByOcupadaFalseOrderByDataAscHoraInicioAsc();
    }

    // Marca uma nova consulta
    public void marcarConsulta(Long pacienteId, Long disponibilidadeId) {

        // Procura o paciente pelo ID
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

        // Procura a disponibilidade pelo ID
        Disponibilidade disponibilidade = disponibilidadeRepository.findById(disponibilidadeId)
                .orElseThrow(() -> new RuntimeException("Disponibilidade não encontrada."));

        // Verifica se a disponibilidade já está ocupada
        if (disponibilidade.getOcupada() == true) {
            throw new RuntimeException("Esta disponibilidade já está ocupada.");
        }

        // Verifica se já existe consulta para esta disponibilidade
        Boolean consultaJaExiste = consultaRepository.existsByDisponibilidade_Id(disponibilidadeId);

        if (consultaJaExiste) {
            throw new RuntimeException("Já existe uma consulta marcada para esta disponibilidade.");
        }

        // Cria a consulta com os dados da disponibilidade
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(disponibilidade.getMedico());
        consulta.setDisponibilidade(disponibilidade);
        consulta.setData(disponibilidade.getData());
        consulta.setHoraInicio(disponibilidade.getHoraInicio());
        consulta.setHoraFim(disponibilidade.getHoraFim());
        consulta.setEstado("Marcada");

        // Marca a disponibilidade como ocupada
        disponibilidade.setOcupada(true);

        // Guarda a disponibilidade atualizada
        disponibilidadeRepository.save(disponibilidade);

        // Guarda a consulta
        consultaRepository.save(consulta);
    }
}