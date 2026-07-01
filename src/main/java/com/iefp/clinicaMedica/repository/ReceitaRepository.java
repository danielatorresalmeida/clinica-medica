package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    // Lista receitas de um paciente específico
    List<Receita> findByPaciente_IdOrderByDataEmissaoDesc(Long pacienteId);

    // Lista receitas de um médico específico
    List<Receita> findByMedico_IdOrderByDataEmissaoDesc(Long medicoId);
}