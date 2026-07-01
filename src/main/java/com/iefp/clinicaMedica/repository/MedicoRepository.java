package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // Procura o médico através do ID do utilizador associado
    Optional<Medico> findByUtilizador_Id(Long utilizadorId);

    // Vai buscar todas as especialidades existentes
    @Query("SELECT DISTINCT m.especialidade FROM Medico m WHERE m.especialidade IS NOT NULL")
    List<String> listarEspecialidades();
}