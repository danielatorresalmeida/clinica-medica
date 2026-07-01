package com.iefp.clinicaMedica.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Consulta {

    // Identificador único da consulta
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Paciente que marcou a consulta
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // Médico responsável pela consulta
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    // Disponibilidade usada para marcar a consulta
    @OneToOne
    @JoinColumn(name = "disponibilidade_id")
    private Disponibilidade disponibilidade;

    // Data da consulta
    private LocalDate data;

    // Hora de início da consulta
    private LocalTime horaInicio;

    // Hora de fim da consulta
    private LocalTime horaFim;

    // Estado da consulta
    // Exemplo: Marcada, Cancelada, Realizada
    private String estado;

    // Construtor vazio obrigatório para o JPA
    public Consulta() {
    }

    // Construtor com todos os campos
    public Consulta(Long id, Paciente paciente, Medico medico, Disponibilidade disponibilidade,
                    LocalDate data, LocalTime horaInicio, LocalTime horaFim, String estado) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.disponibilidade = disponibilidade;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.estado = estado;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public Disponibilidade getDisponibilidade() {
        return disponibilidade;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setDisponibilidade(Disponibilidade disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}