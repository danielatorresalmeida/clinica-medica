package com.iefp.clinicaMedica.model;

public class Paciente {
    private String nome;
    private Integer idade;
    
    //Construtor
    public Paciente(String nome, Integer idade){
        this.nome=nome;
        this.idade=idade;
    }

    public String getNome(){
        return nome;
    }

    public Integer getIdade(){
        return idade;
    }

}
