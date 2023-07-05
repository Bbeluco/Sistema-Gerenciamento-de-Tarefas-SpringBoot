package com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;

public record AtualizacaoUsuarioDTO(
        @Pattern(regexp = "^[a-zA-Z ]*$", message = "Nome deve conter apenas letras e espaços")
        String nome,
        String cargo,

        ArrayList<@Pattern(regexp = "^[a-zA-Z ]*$", message = "Tarefas deve conter apenas letras e espacos") String> tarefas
) {
}
