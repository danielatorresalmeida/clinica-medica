package com.iefp.clinicaMedica.service;

import com.iefp.clinicaMedica.model.Disponibilidade;
import com.iefp.clinicaMedica.model.Medico;
import com.iefp.clinicaMedica.repository.DisponibilidadeRepository;
import com.iefp.clinicaMedica.repository.MedicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DisponibilidadeService {

    // Repositório usado para guardar e procurar disponibilidades
    private final DisponibilidadeRepository disponibilidadeRepository;

    // Repositório usado para procurar médicos
    private final MedicoRepository medicoRepository;

    // Construtor para injetar os repositórios
    public DisponibilidadeService(
            DisponibilidadeRepository disponibilidadeRepository,
            MedicoRepository medicoRepository
    ) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.medicoRepository = medicoRepository;
    }

    // Lista todas as disponibilidades existentes
    public List<Disponibilidade> listarTodas() {
        return disponibilidadeRepository.findAll();
    }

    // Lista disponibilidades livres por especialidade
    public List<Disponibilidade> listarPorEspecialidade(String especialidade) {
        return disponibilidadeRepository
                .findByMedico_EspecialidadeIgnoreCaseAndOcupadaFalseOrderByDataAscHoraInicioAsc(especialidade);
    }

    // Lista disponibilidades de um médico específico
    public List<Disponibilidade> listarPorMedico(Long medicoId) {
        return disponibilidadeRepository.findByMedico_IdOrderByDataAscHoraInicioAsc(medicoId);
    }

    // Cria várias disponibilidades de 1 hora entre a hora de início e a hora de fim
    public void criarDisponibilidade(
            Long medicoId,
            LocalDate data,
            LocalTime horaInicioTrabalho,
            LocalTime horaFimTrabalho
    ) {
        // Procura o médico pelo ID
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

        // Valida se a hora inicial é antes da hora final
        if (!horaInicioTrabalho.isBefore(horaFimTrabalho)) {
            throw new RuntimeException("A hora de início deve ser anterior à hora de fim.");
        }

        // Começa a criar disponibilidades a partir da hora inicial
        LocalTime horaAtual = horaInicioTrabalho;

        // Continua enquanto ainda existir espaço para criar blocos de 1 hora
        while (horaAtual.plusHours(1).isBefore(horaFimTrabalho)
                || horaAtual.plusHours(1).equals(horaFimTrabalho)) {

            // Define o fim da disponibilidade atual
            LocalTime horaFimConsulta = horaAtual.plusHours(1);

            // Verifica se já existe uma disponibilidade igual
            Boolean jaExiste = disponibilidadeRepository
                    .existsByMedico_IdAndDataAndHoraInicioAndHoraFim(
                            medicoId,
                            data,
                            horaAtual,
                            horaFimConsulta
                    );

            // Se ainda não existir, cria uma nova disponibilidade
            if (!jaExiste) {
                Disponibilidade disponibilidade = new Disponibilidade(
                        null,
                        medico,
                        data,
                        horaAtual,
                        horaFimConsulta,
                        false
                );

                // Guarda a disponibilidade na base de dados
                disponibilidadeRepository.save(disponibilidade);
            }

            // Avança para a próxima hora
            horaAtual = horaAtual.plusHours(1);
        }
    }
}