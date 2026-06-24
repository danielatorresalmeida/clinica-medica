package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    
}
