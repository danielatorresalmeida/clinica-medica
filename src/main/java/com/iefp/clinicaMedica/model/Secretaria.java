package com.iefp.clinicaMedica.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Secretaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "utilizador_id", referencedColumnName = "id")
    private Utilizador utilizador;

    public Secretaria() {
    }

    public Secretaria(Long id, Utilizador utilizador) {
        this.id = id;
        this.utilizador = utilizador;
    }

    public Long getId() {
        return id;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
}