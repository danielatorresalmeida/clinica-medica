package com.iefp.clinicaMedica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Especialidade {

    // Identificador único da especialidade
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome da especialidade
    private String nome;

    // Construtor vazio obrigatório para o JPA
    public Especialidade() {
    }

    // Construtor com nome
    public Especialidade(String nome) {
        this.nome = nome;
    }

    // Devolve o ID
    public Long getId() {
        return id;
    }

    // Altera o ID
    public void setId(Long id) {
        this.id = id;
    }

    // Devolve o nome da especialidade
    public String getNome() {
        return nome;
    }

    // Altera o nome da especialidade
    public void setNome(String nome) {
        this.nome = nome;
    }
}