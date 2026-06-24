package com.iefp.clinicaMedica.service;

import com.iefp.clinicaMedica.model.Utilizador;
import com.iefp.clinicaMedica.repository.UtilizadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilizadorService {

    private final UtilizadorRepository utilizadorRepository;

    public UtilizadorService(UtilizadorRepository utilizadorRepository) {
        this.utilizadorRepository = utilizadorRepository;
    }

    public List<Utilizador> getAllUtilizadores() {
        return utilizadorRepository.findAll();
    }
}