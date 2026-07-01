package com.iefp.clinicaMedica.repository;

import com.iefp.clinicaMedica.model.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {

    // Procura utilizador pelo email e senha
    Optional<Utilizador> findByEmailAndSenha(String email, String senha);
}