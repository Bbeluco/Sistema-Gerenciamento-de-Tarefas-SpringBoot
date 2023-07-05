package com.projeto_pessoal.SistemaGerenciamentoTarefas.Controllers;

import com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs.AtualizacaoUsuarioDTO;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs.CriacaoUsuarioDTO;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.Databases.Entities.UsuarioEntity;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.Databases.Repositories.UsuarioRepository;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioEntity> criacaoUsuario(@RequestBody @Validated CriacaoUsuarioDTO criacaoUsuarioDTO){
        return ResponseEntity.status(201).body(this.usuarioService.salvaUsuarioNoBanco(criacaoUsuarioDTO));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> listarTodosUsuarios(){
        return ResponseEntity.status(200).body(this.usuarioService.encontrarTodosUsuarios());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UsuarioEntity> atualizarInformacaoUsuario(@PathVariable int id, @RequestBody @Validated AtualizacaoUsuarioDTO atualizacaoUsuarioDTO){
        return ResponseEntity.status(201).body(this.usuarioService.atualizaUsuarioNoBanco(id, atualizacaoUsuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarRegistroDoBanco(@PathVariable int id) {
        this.usuarioService.deletarUsuarioBanco(id);
        return ResponseEntity.status(204).body(null);
    }

}
