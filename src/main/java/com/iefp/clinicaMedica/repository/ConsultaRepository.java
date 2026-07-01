package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Lista as consultas de um paciente específico
    List<Consulta> findByPaciente_IdOrderByDataAscHoraInicioAsc(Long pacienteId);

    // Lista as consultas de um médico específico
    List<Consulta> findByMedico_IdOrderByDataAscHoraInicioAsc(Long medicoId);

    // Verifica se já existe uma consulta para uma disponibilidade
    Boolean existsByDisponibilidade_Id(Long disponibilidadeId);
}