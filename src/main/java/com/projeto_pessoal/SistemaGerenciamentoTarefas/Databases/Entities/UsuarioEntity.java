package com.projeto_pessoal.SistemaGerenciamentoTarefas.Databases.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Table(name = "usuarios")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    private String cargo;

    private String tarefas;

    @Override
    public String toString(){
        return "Usuario[nome: " + this.nome +", cargo: " + this.cargo + ", tarefas: " + this.tarefas + "]";
    }

}
