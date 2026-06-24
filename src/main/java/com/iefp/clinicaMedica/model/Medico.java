package com.iefp.clinicaMedica.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String especialidade;

    // Composição com Utilizador
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "utilizador_id", referencedColumnName = "id")
    private Utilizador utilizador;

    public Medico() {
    }

    public Medico(Long id, String especialidade, Utilizador utilizador) {
        this.id = id;
        this.especialidade = especialidade;
        this.utilizador = utilizador;
    }

    public Long getId() {
        return id;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
}