package com.iefp.clinicaMedica.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Receita {

    // Identificador único da receita
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Consulta associada à receita
    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    // Paciente que recebe a receita
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // Médico que passou a receita
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    // Nome do medicamento
    private String medicamento;

    // Dosagem do medicamento
    private String dosagem;

    // Instruções de utilização
    private String instrucoes;

    // Data de emissão da receita
    private LocalDate dataEmissao;

    // Estado da receita
    private String estado;

    // Construtor vazio obrigatório para o JPA
    public Receita() {
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}