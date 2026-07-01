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

    // Identificador único do médico
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Especialidade do médico, por exemplo: Cardiologia, Pediatria, etc.
    private String especialidade;

    // Relação entre Médico e Utilizador
    // Cada médico tem um utilizador associado
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "utilizador_id", referencedColumnName = "id")
    private Utilizador utilizador;

    // Construtor vazio obrigatório para o JPA
    public Medico() {
    }

    // Construtor com todos os campos
    public Medico(Long id, String especialidade, Utilizador utilizador) {
        this.id = id;
        this.especialidade = especialidade;
        this.utilizador = utilizador;
    }

    // Devolve o ID do médico
    public Long getId() {
        return id;
    }

    // Altera o ID do médico
    public void setId(Long id) {
        this.id = id;
    }

    // Devolve a especialidade do médico
    public String getEspecialidade() {
        return especialidade;
    }

    // Altera a especialidade do médico
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    // Devolve o utilizador associado ao médico
    public Utilizador getUtilizador() {
        return utilizador;
    }

    // Altera o utilizador associado ao médico
    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
}