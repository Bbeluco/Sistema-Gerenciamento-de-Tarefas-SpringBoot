package com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs;

import com.projeto_pessoal.SistemaGerenciamentoTarefas.Databases.Entities.UsuarioEntity;

import java.util.ArrayList;
import java.util.Arrays;

public class DTOConversor {
    public static CriacaoUsuarioDTO toDTO(UsuarioEntity usuario){
        ArrayList<String> listaTarefas = new ArrayList<>(Arrays.asList(usuario.getTarefas().split(",")));
        return new CriacaoUsuarioDTO(usuario.getNome(), usuario.getCargo(), listaTarefas);
    }
}
