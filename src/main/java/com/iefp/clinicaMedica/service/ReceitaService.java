package com.iefp.clinicaMedica.service;

import com.iefp.clinicaMedica.model.Consulta;
import com.iefp.clinicaMedica.model.Receita;
import com.iefp.clinicaMedica.repository.ConsultaRepository;
import com.iefp.clinicaMedica.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReceitaService {

    // Repositório das receitas
    private final ReceitaRepository receitaRepository;

    // Repositório das consultas
    private final ConsultaRepository consultaRepository;

    // Construtor para injetar os repositórios
    public ReceitaService(ReceitaRepository receitaRepository, ConsultaRepository consultaRepository) {
        this.receitaRepository = receitaRepository;
        this.consultaRepository = consultaRepository;
    }

    // Lista todas as receitas
    public List<Receita> listarTodas() {
        return receitaRepository.findAll();
    }

    // Lista receitas de um paciente específico
    public List<Receita> listarPorPaciente(Long pacienteId) {
        return receitaRepository.findByPaciente_IdOrderByDataEmissaoDesc(pacienteId);
    }

    // Lista receitas de um médico específico
    public List<Receita> listarPorMedico(Long medicoId) {
        return receitaRepository.findByMedico_IdOrderByDataEmissaoDesc(medicoId);
    }

    // Lista todas as consultas para aparecerem no formulário
    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    // Cria uma nova receita associada a uma consulta
    public void criarReceita(
            Long consultaId,
            String medicamento,
            String dosagem,
            String instrucoes
    ) {
        // Procura a consulta pelo ID
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada."));

        // Cria a receita
        Receita receita = new Receita();

        // Liga a receita à consulta
        receita.setConsulta(consulta);

        // Copia o paciente e o médico da consulta
        receita.setPaciente(consulta.getPaciente());
        receita.setMedico(consulta.getMedico());

        // Define os dados da receita
        receita.setMedicamento(medicamento);
        receita.setDosagem(dosagem);
        receita.setInstrucoes(instrucoes);
        receita.setDataEmissao(LocalDate.now());
        receita.setEstado("Registada");

        // Guarda a receita
        receitaRepository.save(receita);
    }
}