package com.iefp.clinicaMedica.service;

import com.iefp.clinicaMedica.model.Consulta;
import com.iefp.clinicaMedica.model.Exame;
import com.iefp.clinicaMedica.repository.ConsultaRepository;
import com.iefp.clinicaMedica.repository.ExameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExameService {

    // Repositório dos exames
    private final ExameRepository exameRepository;

    // Repositório das consultas
    private final ConsultaRepository consultaRepository;

    // Construtor para injetar os repositórios
    public ExameService(ExameRepository exameRepository, ConsultaRepository consultaRepository) {
        this.exameRepository = exameRepository;
        this.consultaRepository = consultaRepository;
    }

    // Lista todos os exames
    public List<Exame> listarTodos() {
        return exameRepository.findAll();
    }

    // Lista exames de um paciente específico
    public List<Exame> listarPorPaciente(Long pacienteId) {
        return exameRepository.findByPaciente_IdOrderByDataPedidoDesc(pacienteId);
    }

    // Lista exames de um médico específico
    public List<Exame> listarPorMedico(Long medicoId) {
        return exameRepository.findByMedico_IdOrderByDataPedidoDesc(medicoId);
    }

    // Lista todas as consultas para aparecerem no formulário
    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    // Cria um novo exame associado a uma consulta
    public void criarExame(Long consultaId, String tipo, String descricao) {

        // Procura a consulta pelo ID
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada."));

        // Cria o exame
        Exame exame = new Exame();

        // Associa a consulta ao exame
        exame.setConsulta(consulta);

        // Copia o paciente e o médico da consulta
        exame.setPaciente(consulta.getPaciente());
        exame.setMedico(consulta.getMedico());

        // Define os dados do exame
        exame.setTipo(tipo);
        exame.setDescricao(descricao);
        exame.setResultado("Pendente");
        exame.setDataPedido(LocalDate.now());
        exame.setEstado("Marcado");

        // Guarda o exame
        exameRepository.save(exame);
    }
}