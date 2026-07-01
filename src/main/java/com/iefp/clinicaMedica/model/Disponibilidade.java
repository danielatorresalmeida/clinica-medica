package com.iefp.clinicaMedica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Disponibilidade {

    // Identificador único da disponibilidade
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cada disponibilidade pertence a um médico
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    // Dia da disponibilidade
    private LocalDate data;

    // Hora de início da consulta/disponibilidade
    private LocalTime horaInicio;

    // Hora de fim da consulta/disponibilidade
    private LocalTime horaFim;

    // Indica se a disponibilidade já está ocupada ou não
    private Boolean ocupada = false;
}