package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // Procura o paciente através do ID do utilizador associado
    Optional<Paciente> findByUtilizador_Id(Long utilizadorId);
}