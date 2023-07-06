package com.projeto_pessoal.SistemaGerenciamentoTarefas.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs.CriacaoUsuarioDTO;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs.DTOConversor;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.DTOs.ErrosGenericosDTO;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.Databases.Entities.UsuarioEntity;
import com.projeto_pessoal.SistemaGerenciamentoTarefas.Services.UsuarioService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UsuarioControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private UsuarioService usuarioService;


	@BeforeEach
	private void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	void deveRetornarSucessoQuandoCriarUsuarioValidoPOST() throws Exception {
		Mockito.when(this.usuarioService.salvaUsuarioNoBanco(mockUsuarioRequisicao())).thenReturn(retornoMockado());

		MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
								.contentType(MediaType.APPLICATION_JSON).content(criaJsonSemParametro("teste").toString())).andReturn();

		CriacaoUsuarioDTO resultadoConvertidoParaDtoCriacaoUsuario = converteParaDTOCriacaoUsuario(resultado.getResponse().getContentAsString());

		assertEquals(resultado.getResponse().getStatus(), 201);
		assertEquals(resultadoConvertidoParaDtoCriacaoUsuario.nome(), "teste");
		assertEquals(resultadoConvertidoParaDtoCriacaoUsuario.cargo(), "cargoTeste");
		assertEquals(resultadoConvertidoParaDtoCriacaoUsuario.tarefas().toString(), "[tarefasTeste, tarefaPosTeste]");
	}

	@Test
	// OBJETIVO: Conseguir validar a resposta dada pelo endpoint em formato de lista
	void deveRetornarUsuariosCadastradoBDGET() throws Exception {
		Mockito.when(this.usuarioService.encontrarTodosUsuarios()).thenReturn(conversorParaListaDeEntidadesUsuario(retornoMockado()));
		MvcResult resultado = this.mockMvc.perform(MockMvcRequestBuilders.get("/usuarios").contentType(MediaType.APPLICATION_JSON)).andReturn();

		converteParaDTOCriacaoUsuarioLista(resultado.getResponse().getContentAsString());
		System.out.println(resultado.getResponse().getContentAsString());
	}

	@Test
	// OBJETIVO: Conseguir validar a resposta dada pelo endpoint apos atualizar o usuario
	void deveConsultarAtualizacaoDeUsuarioPUT(){

	}

	@Test
	// OBJETIVO: Validar o status de retorno
	void deveApagarOUsuarioDELETE(){

	}

	@Test
	void deveRetornarErroQuandoNaoEnviarParametroNome() throws Exception {
		JSONObject usuarioJson = criaJsonSemParametro("nome");


		MvcResult resultadoRequisicao = this.mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
											.contentType(MediaType.APPLICATION_JSON).content(usuarioJson.toString())).andReturn();

		String bodyRecebido = resultadoRequisicao.getResponse().getContentAsString();
		ErrosGenericosDTO erroEsperado = new ErrosGenericosDTO(HttpStatus.BAD_REQUEST, "Nome e um campo obrigatorio e deve ser enviado");

		assertEquals(erroEsperado, converteParaDTOErroGenerico(bodyRecebido));
	}

	@Test
	void deveRetornarErroQuandoNaoEnviarParametroCargo() throws Exception {
		JSONObject usuarioJson = criaJsonSemParametro("cargo");

		MvcResult resultadoRequisicao = this.mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
											.contentType(MediaType.APPLICATION_JSON).content(usuarioJson.toString())).andReturn();

		ErrosGenericosDTO resultadoConvertidoParaDTOErros = converteParaDTOErroGenerico(resultadoRequisicao.getResponse().getContentAsString());
		ErrosGenericosDTO resultadoEsperado = new ErrosGenericosDTO(HttpStatus.BAD_REQUEST, "Cargo e um campo obrigatorio e deve ser enviado");


		assertEquals(resultadoConvertidoParaDTOErros, resultadoEsperado);
	}

	@Test
	void deveRetornarErroQuandoNaoEnviarParametroTarefas() throws Exception {
		JSONObject usuarioJson = criaJsonSemParametro("tarefas");

		MvcResult resultadoRequisicao = this.mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
											.contentType(MediaType.APPLICATION_JSON).content(usuarioJson.toString())).andReturn();

		ErrosGenericosDTO resultadoConvertidoParaDTOErros = converteParaDTOErroGenerico(resultadoRequisicao.getResponse().getContentAsString());
		ErrosGenericosDTO resultadoEsperado = new ErrosGenericosDTO(HttpStatus.BAD_REQUEST, "Tarefas e um campo obrigatorio e deve ser enviado");

		assertEquals(resultadoConvertidoParaDTOErros, resultadoEsperado);
	}

	@Test
	void deveRetornarErroQuandoEnviarParametroTarefasContendoUmCaractereInvalido() throws Exception {
		JSONObject usuario = criaJsonSemParametro("tarefas");
		usuario.put("tarefas", new JSONArray().put("teste123"));

		MvcResult resultadoRecebido = this.mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
										.contentType(MediaType.APPLICATION_JSON).content(usuario.toString())).andReturn();

		ErrosGenericosDTO resultadoConvertidoParaDTOErros = converteParaDTOErroGenerico(resultadoRecebido.getResponse().getContentAsString());
		ErrosGenericosDTO resultadoEsperado = new ErrosGenericosDTO(HttpStatus.BAD_REQUEST, "Tarefas deve conter apenas letras e espacos");

		assertEquals(resultadoConvertidoParaDTOErros, resultadoEsperado);
	}

	private ErrosGenericosDTO converteParaDTOErroGenerico(String json) throws IOException {
		ObjectMapper objTest = new ObjectMapper();
		return objTest.readValue(json, ErrosGenericosDTO.class);
	}

	private CriacaoUsuarioDTO converteParaDTOCriacaoUsuario(String json) throws IOException {
		ObjectMapper objTest = new ObjectMapper();
		UsuarioEntity usuario = objTest.readValue(json, UsuarioEntity.class);

		return DTOConversor.toDTO(usuario);
	}

	private JSONObject criaJsonSemParametro(String parametroParaNaoUtilizar) {
		JSONObject usuario = new JSONObject();
		JSONArray tarefas = new JSONArray();
		tarefas.put("teste");
		try {
			usuario.put("nome", "Bruno");
			usuario.put("cargo", "QA");
			usuario.put("tarefas", tarefas);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}


		usuario.remove(parametroParaNaoUtilizar);

		return usuario;
	}

	private static CriacaoUsuarioDTO mockUsuarioRequisicao() {
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("teste");
		CriacaoUsuarioDTO dto = new CriacaoUsuarioDTO("Bruno", "QA", arr);
		return dto;
	}

	private static UsuarioEntity retornoMockado() {
		UsuarioEntity usuarioRetorno = new UsuarioEntity();
		usuarioRetorno.setNome("teste");
		usuarioRetorno.setCargo("cargoTeste");
		usuarioRetorno.setTarefas("tarefasTeste,tarefaPosTeste");
		return usuarioRetorno;
	}

	private static List<UsuarioEntity> conversorParaListaDeEntidadesUsuario(UsuarioEntity usuario){
		List<UsuarioEntity> listaUsuarios = new ArrayList<>();
		listaUsuarios.add(usuario);
		return listaUsuarios;
	}
}
