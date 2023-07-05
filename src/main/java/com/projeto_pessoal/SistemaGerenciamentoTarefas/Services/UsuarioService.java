package com.projeto_pessoal.SistemaGerenciamentoTarefas.Services;

import com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs.AtualizacaoUsuarioDTO;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs.CriacaoUsuarioDTO;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.Databases.Entities.UsuarioEntity;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.Databases.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioEntity salvaUsuarioNoBanco(CriacaoUsuarioDTO criacaoUsuarioDTO){
        UsuarioEntity usuario = entidadeCriacaoDeUsuario(criacaoUsuarioDTO);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public List<UsuarioEntity> encontrarTodosUsuarios(){
        return usuarioRepository.findAll();
    }

    public UsuarioEntity atualizaUsuarioNoBanco(int id, AtualizacaoUsuarioDTO atualizacaoUsuarioDTO) {
        UsuarioEntity usuario = entidadeAtualizacaoDeUsuario(id, atualizacaoUsuarioDTO);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public void deletarUsuarioBanco(int id){
        usuarioRepository.deleteById(id);
    }

    private UsuarioEntity entidadeCriacaoDeUsuario(CriacaoUsuarioDTO criacaoUsuarioDTO) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(criacaoUsuarioDTO.nome());
        usuario.setCargo(criacaoUsuarioDTO.cargo());
        usuario.setTarefas(String.join(",", criacaoUsuarioDTO.tarefas()));

        return usuario;
    }

    private UsuarioEntity entidadeAtualizacaoDeUsuario(int id, AtualizacaoUsuarioDTO atualizacaoUsuarioDTO) {
        UsuarioEntity usuario = usuarioRepository.getReferenceById(id);

        if(atualizacaoUsuarioDTO.nome() != null)
            usuario.setNome(atualizacaoUsuarioDTO.nome());
        if(atualizacaoUsuarioDTO.cargo() != null)
            usuario.setCargo(atualizacaoUsuarioDTO.cargo());
        if(atualizacaoUsuarioDTO.tarefas() != null)
            usuario.setTarefas(String.join(",", atualizacaoUsuarioDTO.tarefas()));

        return usuario;
    }
}
