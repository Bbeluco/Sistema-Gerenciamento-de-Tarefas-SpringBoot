package com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;

public record CriacaoUsuarioDTO(

        @NotBlank(message = "Nome e um campo obrigatorio e deve ser enviado")
        @Pattern(regexp = "^[a-zA-Z ]*$", message = "Nome deve conter apenas letras e espaços")
        String nome,
        @NotBlank(message = "Cargo e um campo obrigatorio e deve ser enviado")
        String cargo,

        @NotNull(message = "Tarefas e um campo obrigatorio e deve ser enviado")
        ArrayList<@Pattern(regexp = "^[a-zA-Z ]*$", message = "Tarefas deve conter apenas letras e espacos") String> tarefas
) {
}
