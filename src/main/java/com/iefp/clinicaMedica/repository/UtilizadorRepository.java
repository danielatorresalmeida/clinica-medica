package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {
}