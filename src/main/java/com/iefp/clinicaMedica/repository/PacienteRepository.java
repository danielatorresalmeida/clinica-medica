package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
