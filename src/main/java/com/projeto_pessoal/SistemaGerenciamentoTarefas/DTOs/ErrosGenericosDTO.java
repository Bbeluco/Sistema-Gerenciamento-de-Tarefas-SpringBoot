package com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs;

import org.springframework.http.HttpStatus;

public record ErrosGenericosDTO(HttpStatus status, String mensagem) { }
