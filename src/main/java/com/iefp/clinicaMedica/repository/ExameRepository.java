package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {

    // Lista exames de um paciente específico
    List<Exame> findByPaciente_IdOrderByDataPedidoDesc(Long pacienteId);

    // Lista exames de um médico específico
    List<Exame> findByMedico_IdOrderByDataPedidoDesc(Long medicoId);
}