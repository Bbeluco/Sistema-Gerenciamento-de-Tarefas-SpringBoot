package com.projeto_pessoal.SistemaGerenciamentoTarefas.Repositories;

import com.projeto_pessoal.SistemaGerenciamentoTarefas.Databases.Entities.UsuarioEntity;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.Databases.Repositories.UsuarioRepository;

import org.junit.Assert;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration-test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Tag("IntegrationTest")
    public void deveSalvarUsuarioQuandoEnviarUmaEntidade(){
        assertTrue(salvarUsuarioBanco().getId() > 0);
    }

    @Test
    @Tag("IntegrationTest")
    public void deveRetornarUsuariosSalvosNoBancoQuandoOConsultar() {
        UsuarioEntity usuario = salvarUsuarioBanco();
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        assertTrue(usuarios.size() >= 1);

        UsuarioEntity usuarioCadastradoNoBanco = usuarioRepository.getReferenceById(usuario.getId());

        assertEquals(usuarioCadastradoNoBanco.getNome(), "teste integracao");
        assertEquals(usuarioCadastradoNoBanco.getCargo(), "integracao");
        assertEquals(usuarioCadastradoNoBanco.getTarefas(), "in,te,gra,cao");
    }

    @Test
    @Tag("IntegrationTest")
    public void deveAtualizarCadastroUsuarioQuandoSolicitado() {
        int idUsuario = salvarUsuarioBanco().getId();

        final String novoNome = "alterado teste unitario";

        UsuarioEntity usuario = usuarioRepository.getReferenceById(idUsuario);
        usuario.setNome(novoNome);
        usuarioRepository.save(usuario);

        usuarioRepository.findById(usuario.getId());

        assertEquals(usuarioRepository.findById(usuario.getId()).get().getNome(), novoNome);
    }

    @Test
    @Tag("IntegrationTest")
    public void deveDeletarUsuarioQuandoSolicitado(){
        int idUsuario = salvarUsuarioBanco().getId();
        usuarioRepository.deleteById(idUsuario);
        usuarioRepository.findById(idUsuario);
        assertTrue(usuarioRepository.findById(idUsuario).isEmpty());
    }

    private UsuarioEntity salvarUsuarioBanco(){
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("teste integracao");
        usuario.setCargo("integracao");
        usuario.setTarefas("in,te,gra,cao");

        usuarioRepository.save(usuario);

        return usuario;
    }
}
