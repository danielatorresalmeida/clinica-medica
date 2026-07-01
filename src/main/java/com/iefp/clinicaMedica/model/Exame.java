package com.iefp.clinicaMedica.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Exame {

    // Identificador único do exame
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Consulta associada ao exame
    @ManyToOne
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    // Paciente que vai realizar o exame
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // Médico que pediu o exame
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;

    // Tipo de exame, por exemplo: Raio-X, Análises, Ecografia
    private String tipo;

    // Descrição ou observações do exame
    private String descricao;

    // Resultado do exame
    private String resultado;

    // Estado do exame, por exemplo: Marcado ou Cancelado
    private String estado;

    // Data em que o exame foi pedido
    private LocalDate dataPedido;

    // Construtor vazio obrigatório para o JPA
    public Exame() {
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

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getResultado() {
        return resultado;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getEstado() {
    return estado;
    }

    public void setEstado(String estado) {
    this.estado = estado;
    }
}