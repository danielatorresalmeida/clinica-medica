package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Disponibilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {

    // Procura disponibilidades livres pela especialidade do médico
    List<Disponibilidade> findByMedico_EspecialidadeIgnoreCaseAndOcupadaFalseOrderByDataAscHoraInicioAsc(
            String especialidade
    );

    // Lista todas as disponibilidades de um médico específico
    List<Disponibilidade> findByMedico_IdOrderByDataAscHoraInicioAsc(
            Long medicoId
    );

    // Lista apenas disponibilidades livres
    List<Disponibilidade> findByOcupadaFalseOrderByDataAscHoraInicioAsc();

    // Lista apenas disponibilidades livres de um médico específico
    List<Disponibilidade> findByMedico_IdAndOcupadaFalseOrderByDataAscHoraInicioAsc(Long medicoId);

    // Verifica se já existe uma disponibilidade igual para o mesmo médico
    Boolean existsByMedico_IdAndDataAndHoraInicioAndHoraFim(
            Long medicoId,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim
    );
}