package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

    // Lista as especialidades por ordem alfabética
    List<Especialidade> findAllByOrderByNomeAsc();
}