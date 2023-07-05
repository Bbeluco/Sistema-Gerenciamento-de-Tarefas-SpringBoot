package com.projeto_pessoal.SistemaGerenciamentoTarefas.Errors;

import com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs.ErrosGenericosDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrosGenericos {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrosGenericosDTO> erroPorEnviarParametroForaDoContratoEstabelecido(MethodArgumentNotValidException erro){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrosGenericosDTO(HttpStatus.BAD_REQUEST, erro.getFieldErrors().get(0).getDefaultMessage()));
    }
}
