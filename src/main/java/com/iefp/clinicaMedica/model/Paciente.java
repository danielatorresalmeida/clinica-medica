package com.iefp.clinicaMedica.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;

@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataNascimento;
    private String telefone;
    private String endereco;

    // Composição com Utilizador
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "utilizador_id", referencedColumnName = "id")
    private Utilizador utilizador;

    public Paciente() {
    }

    public Paciente(Long id, Utilizador utilizador) {
        this.id = id;
        this.utilizador = utilizador;
    }

    public Paciente(Long id, LocalDate dataNascimento, String telefone, String endereco, Utilizador utilizador) {
        this.id = id;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.utilizador = utilizador;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
}